package dk.dtu.weatherapp.domain

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import dk.dtu.weatherapp.Firebase.FirebaseHelper
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

    private val mutableRecentLocationFlow = MutableSharedFlow<List<Location>>()
    val recentLocationFlow = mutableFavoriteLocationFlow.asSharedFlow()

    suspend fun getLocations(input: String) = mutableLocationFlow.emit(
        searchForCities(input)
    )

    suspend fun getFavoriteLocations() = mutableFavoriteLocationFlow.emit(
        fetchFavorites()
    )

    suspend fun getRecentLocations() = mutableRecentLocationFlow.emit(
        fetchFavorites() // TODO: Change to recent
    )

    fun toggleFavorite(location: Location) {
        FirebaseHelper.isFavoriteInFirestore(
            userId = userId,
            cityName = location.name,
            onSuccess = { favorite ->
                if (favorite) {
                    FirebaseHelper.removeFavoriteFromFirestore(
                        userId = userId,
                        cityName = location.name,
                        onSuccess = {
                            Log.d("LocationRepository", "Removed favorite for ${location.name}")
                        },
                        onFailure = { exception ->
                            println("Error removing favorite: ${exception.message}")
                        }
                    )
                } else {
                    FirebaseHelper.saveFavoriteToFirestore(
                        userId = userId,
                        cityName = location.name,
                        onSuccess = {
                            Log.d("LocationRepository", "Added favorite for ${location.name}")
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


        /*val favoritesCollection = firestore.collection("users")
            .document(userId)
            .collection("favorites")

        Log.d("LocationRepository", "Toggling favorite for ${location.name}")
        // Toggle a city

        favoritesCollection.document(location.name).get().addOnFailureListener { document ->
                favoritesCollection.document(location.name).set(mapOf("name" to location.name))
        }.addOnSuccessListener { document ->
            favoritesCollection.document(location.name).delete()
        }*/
    }

    fun searchForCities(query: String): List<Location> {
        val inputStream = getAppContext().resources.openRawResource(R.raw.cities_all)
        return inputStream.bufferedReader().use { reader ->
            reader.lineSequence()
                .drop(1) // Skip the first line
                .filter { it.split(",")[1].contains(query, ignoreCase = true) }
                .take(30)
                .map {
                    Location(name = it.split(",")[1], 15.5, R.drawable.i01n, isFavorite = false).apply {
                        FirebaseHelper.isFavoriteInFirestore(
                            userId = userId,
                            cityName = it.split(",")[1],
                            onSuccess = { favorite ->
                                isFavorite = favorite
                                Log.d("LocationRepository", "Favorite status for ${it.split(",")[1]}: $favorite")
                            },
                            onFailure = { exception ->
                                println("Error checking favorite status: ${exception.message}")
                            }
                        )
                    }
                }
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
                Location(name = cityName, 15.5, R.drawable.i01n, true)
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
                    Location(name = cityName, 15.5, R.drawable.i01n, true)
                }
                kotlinx.coroutines.GlobalScope.launch {
                    mutableFavoriteLocationFlow.emit(favoriteCities)
                }
            }
        }

        return initialFavorites
    }


}