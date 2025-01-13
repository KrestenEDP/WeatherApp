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

    private var favorites: List<String> = emptyList() // Purely to see if search results are in favorites or not

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
                    val parameters = it.split(",")
                    Location(name = parameters[0], parameters[1], parameters[2], isFavorite = favorites.contains(parameters[0]))
                }
                .toList()
        }
    }

    suspend fun fetchFavorites(): List<Location> {
        favorites = emptyList()
        val favoritesCollection = firestore.collection("users")
            .document(userId)
            .collection("favorites")

        val initialFavorites = favoritesCollection.get()
            .await()
            .documents.map { document ->
                val cityName = document.id
                favorites += cityName
                Location(name = cityName, "15.5", "15.5", true)
            }

        favoritesCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val favoriteCities = snapshot.documents.map { document ->
                    val cityName = document.id
                    favorites += cityName
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