package dk.dtu.weatherapp.domain

import com.google.firebase.firestore.FirebaseFirestore
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.getAppContext
import dk.dtu.weatherapp.models.Location
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class LocationRepository(private val userId: String) {
    private val firestore = FirebaseFirestore.getInstance()

    private val mutableLocationFlow = MutableSharedFlow<List<Location>>()
    val locationFlow = mutableLocationFlow.asSharedFlow()

    private val mutableFavoriteLocationFlow = MutableSharedFlow<List<Location>>()
    val favoriteLocationFlow = mutableFavoriteLocationFlow.asSharedFlow()

    suspend fun getLocations(input: String) = mutableLocationFlow.emit(
        searchForCities(input)
    )

    suspend fun getFavoriteLocations() = mutableFavoriteLocationFlow.emit(
        fetchFavorites()
    )

    fun searchForCities(query: String): List<Location> {
        val inputStream = getAppContext().resources.openRawResource(R.raw.cities_all)
        return inputStream.bufferedReader().use { reader ->
            reader.lineSequence()
                .drop(1) // Skip the first line
                .filter { it.split(",")[1].contains(query, ignoreCase = true) } // Filter based on name (index 1)
                .take(30) // Limit to 30 results
                .map { Location(name = it.split(",")[1], 15.5, R.drawable.i01n) } // Map to Location
                .toList() // Convert to list
        }
    }

    suspend fun fetchFavorites(): List<Location> {
        val deferred = CompletableDeferred<List<Location>>()

        val favoritesCollection = firestore.collection("users")
            .document(userId)
            .collection("favorites")

        favoritesCollection.get()
            .addOnSuccessListener { result ->
                val favoriteCities = mutableListOf<Location>()
                for (document in result) {
                    val cityName = document.id
                    favoriteCities.add(Location(name = cityName, 15.5, R.drawable.i01n))
                }
                deferred.complete(favoriteCities)
            }
            .addOnFailureListener { e ->
                deferred.complete(emptyList())
            }

        return deferred.await()
    }
}