package dk.dtu.weatherapp.domain

import com.google.firebase.firestore.FirebaseFirestore
import dk.dtu.weatherapp.Firebase.FirebaseHelper
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.getAppContext
import dk.dtu.weatherapp.models.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LocationRepository(private val userId: String) {
    private val firestore = FirebaseFirestore.getInstance()

    private var favorites: List<String> = emptyList()

    private val mutableLocationFlow = MutableSharedFlow<List<Location>>()
    val locationFlow = mutableLocationFlow.asSharedFlow()

    private val mutableFavoriteLocationFlow = MutableSharedFlow<List<Location>>()
    val favoriteLocationFlow = mutableFavoriteLocationFlow.asSharedFlow()

    private val mutableRecentLocationFlow = MutableSharedFlow<List<Location>>()
    val recentLocationFlow = mutableRecentLocationFlow.asSharedFlow()

    suspend fun getLocations(input: String) = mutableLocationFlow.emit(
        searchForCities(input)
    )

    suspend fun getFavoriteLocations() = mutableFavoriteLocationFlow.emit(
        fetchCollection("favorites")
    )

    suspend fun getRecentLocations() = mutableRecentLocationFlow.emit(
        fetchCollection("recent")
    )

    fun toggleFavorite(location: Location) {
        FirebaseHelper.isLocationInFirestore(
            userId = userId,
            tableId = "favorites",
            cityName = location.name,
            onSuccess = { favorite ->
                if (favorite) {
                    // Remove the favorite
                    FirebaseHelper.removeLocationFromFirestore(
                        userId = userId,
                        tableId = "favorites",
                        cityName = location.name,
                        onSuccess = {
                            GlobalScope.launch(Dispatchers.Main) {
                                getFavoriteLocations()
                            }
                        },
                        onFailure = { exception ->
                            println("Error removing favorite: ${exception.message}")
                        }
                    )
                } else {
                    FirebaseHelper.saveLocationToFirestore(
                        userId = userId,
                        tableId = "favorites",
                        cityName = location.name,
                        latitude = location.lat,
                        longitude = location.lon,
                        onSuccess = {
                            GlobalScope.launch(Dispatchers.Main) {
                                getFavoriteLocations()
                            }
                        },
                        onFailure = { exception ->
                            println("Error saving favorite: ${exception.message}")
                        }
                    )
                }
            },
            onFailure = { exception ->
                println("Error checking favorite status: ${exception.message}")
            }
        )
    }

    fun searchForCities(query: String): List<Location> {
        val inputStream = getAppContext().resources.openRawResource(R.raw.cities)

        return inputStream.bufferedReader().use { reader ->
            reader.lineSequence()
                .drop(1)
                .filter { it.split(",")[0].contains(query, ignoreCase = true) }
                .take(15)
                .map {
                    val parameters = it.split(",")
                    Location(name = parameters[0], parameters[1], parameters[2], isFavorite = favorites.contains(parameters[0]))
                }
                .toList()
        }
    }

    suspend fun fetchCollection(tableId: String): List<Location> {
        if (tableId == "favorites") favorites = emptyList()
        val collection = firestore.collection("users")
            .document(userId)
            .collection(tableId)

        val initialCities = collection.get()
            .await()
            .documents.map { document ->
                val cityName = document.id
                val lat: String = document["latitude"] as String
                val lon: String = document["longitude"] as String
                if (tableId == "favorites") favorites += cityName
                Location(name = cityName, lat, lon, if (tableId == "favorites") true else favorites.contains(cityName))
            }

        collection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val cities = snapshot.documents.map { document ->
                    val cityName = document.id
                    val lat: String = document["latitude"] as String
                    val lon: String = document["longitude"] as String
                    if (tableId == "favorites") favorites += cityName
                    Location(name = cityName, lat, lon, if (tableId == "favorites") true else favorites.contains(cityName))
                }
                GlobalScope.launch {
                    if (tableId == "favorites") mutableFavoriteLocationFlow.emit(cities)
                    else mutableRecentLocationFlow.emit(cities)
                }
            }
        }

        return initialCities
    }
}