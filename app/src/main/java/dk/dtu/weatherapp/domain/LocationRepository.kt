package dk.dtu.weatherapp.domain

import android.util.Log
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
                    // Remove the favorite
                    FirebaseHelper.removeFavoriteFromFirestore(
                        userId = userId,
                        cityName = location.name,
                        onSuccess = {
                            Log.d("LocationRepository", "Removed favorite for ${location.name}")
                            GlobalScope.launch(Dispatchers.Main) {
                                getFavoriteLocations()
                            }
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
                .take(30)
                .map {
                    Location(name = it.split(",")[0], it.split(",")[1], it.split(",")[2], isFavorite = false).apply {
                        FirebaseHelper.isFavoriteInFirestore(
                            userId = userId,
                            cityName = it.split(",")[0],
                            onSuccess = { favorite ->
                                isFavorite = favorite
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
            .await()
            .documents.map { document ->
                val cityName = document.id
                Location(name = cityName, "15.5", "15.5", true)
            }

        favoritesCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val favoriteCities = snapshot.documents.map { document ->
                    val cityName = document.id
                    Location(name = cityName, "15.5", "15.5", true)
                }
                GlobalScope.launch {
                    mutableFavoriteLocationFlow.emit(favoriteCities)
                }
            }
        }

        return initialFavorites
    }


}