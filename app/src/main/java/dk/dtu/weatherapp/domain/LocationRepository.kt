package dk.dtu.weatherapp.domain

import com.google.firebase.firestore.FirebaseFirestore
import dk.dtu.weatherapp.firebase.FirebaseHelper
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

    private val mutableFavoriteLocationFlow = MutableSharedFlow<List<Location>?>()
    val favoriteLocationFlow = mutableFavoriteLocationFlow.asSharedFlow()

    private val mutableRecentLocationFlow = MutableSharedFlow<List<Location>?>()
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
        FirebaseHelper.isLocationInFireStore(
            userId = userId,
            tableId = "favorites",
            cityName = location.name,
            onSuccess = { favorite ->
                if (favorite) {
                    FirebaseHelper.removeLocationFromFireStore(
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
                    FirebaseHelper.saveLocationToFireStore(
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

    private fun searchForCities(query: String): List<Location> {
        val inputStream = getAppContext().resources.openRawResource(R.raw.cities)

        return inputStream.bufferedReader().use { reader ->
            reader.lineSequence()
                .drop(1)
                .filter { it.split(",")[0].contains(query, ignoreCase = true) }
                .take(30)
                .map {
                    val parameters = it.split(",")
                    Location(name = parameters[0], parameters[1], parameters[2], isFavorite = favorites.contains(parameters[0]))
                }
                .toList()
        }
    }

    private suspend fun fetchCollection(tableId: String): List<Location>? {
        try {
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

            // Sort by timestamp if recent
            val sortedInitialCities = if (tableId == "recent") {
                initialCities.sortedByDescending { it.second }
                    .map { it.first }
            } else {
                initialCities.map { it.first }
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
                        cities.sortedByDescending { it.second }
                            .map { it.first }
                    } else {
                        cities.map { it.first }
                    }

                    customScope.launch {
                        if (tableId == "favorites") mutableFavoriteLocationFlow.emit(sortedCities)
                        else mutableRecentLocationFlow.emit(sortedCities)
                    }
                }
            }

            return sortedInitialCities
        } catch (e: Exception) {
            println("Error fetching collection: ${e.message}")
            return null
        }
    }
}