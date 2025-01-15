package dk.dtu.weatherapp.domain

import com.google.firebase.firestore.FirebaseFirestore
import dk.dtu.weatherapp.Firebase.FirebaseHelper
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.getAppContext
import dk.dtu.weatherapp.models.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LocationRepository(private val userId: String) {
    private val customScope = CoroutineScope(Dispatchers.IO)
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
                            customScope.launch(Dispatchers.Main) {
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
                            customScope.launch(Dispatchers.Main) {
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
                val timestamp = document["timestamp"] as? com.google.firebase.Timestamp
                if (tableId == "favorites") favorites += cityName
                Location(
                    name = cityName,
                    lat = lat,
                    lon = lon,
                    isFavorite = if (tableId == "favorites") true else favorites.contains(cityName)
                ) to timestamp?.seconds            }

        val sortedInitialCities = if (tableId == "recent") {
            initialCities.sortedByDescending { it.second } // Sort by the timestamp (second part of the pair)
                .map { it.first } // Extract sorted Location objects (ignore timestamps)
        } else {
            initialCities.map { it.first } // Just map Location objects without sorting
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
                    val timestamp = document["timestamp"] as? com.google.firebase.Timestamp
                    if (tableId == "favorites") favorites += cityName
                    Location(
                        name = cityName,
                        lat = lat,
                        lon = lon,
                        isFavorite = if (tableId == "favorites") true else favorites.contains(cityName)
                    ) to timestamp?.seconds
                }

                val sortedCities = if (tableId == "recent") {
                    cities.sortedByDescending { it.second } // Sort by the timestamp (second part of the pair)
                        .map { it.first } // Extract sorted Location objects (ignore timestamps)
                } else {
                    cities.map { it.first } // Just map Location objects without sorting
                }

                customScope.launch {
                    if (tableId == "favorites") mutableFavoriteLocationFlow.emit(sortedCities)
                    else mutableRecentLocationFlow.emit(sortedCities)
                }
            }
        }

        return sortedInitialCities
    }
}