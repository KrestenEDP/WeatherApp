package dk.dtu.weatherapp.domain

import com.google.firebase.firestore.FirebaseFirestore
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.getAppContext
import dk.dtu.weatherapp.models.Location
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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
                .filter { it.split(",")[1].contains(query, ignoreCase = true) }
                .take(30)
                .map { Location(name = it.split(",")[1], 15.5, R.drawable.i01n) }
                .toList()
        }
    }

    suspend fun fetchFavorites(): List<Location> {
        val favoritesCollection = firestore.collection("users")
            .document(userId)
            .collection("favorites")

        val initialFavorites = favoritesCollection.get()
            .await() // Requires `kotlinx-coroutines-play-services` for `Task.await()`
            .documents.map { document ->
                val cityName = document.id
                Location(name = cityName, 15.5, R.drawable.i01n)
            }

        // Set up the real-time listener
        favoritesCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                // Handle error (e.g., log it or show a message)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val favoriteCities = snapshot.documents.map { document ->
                    val cityName = document.id
                    Location(name = cityName, 15.5, R.drawable.i01n)
                }
                kotlinx.coroutines.GlobalScope.launch {
                    mutableFavoriteLocationFlow.emit(favoriteCities)
                }
            }
        }

        return initialFavorites
    }


}