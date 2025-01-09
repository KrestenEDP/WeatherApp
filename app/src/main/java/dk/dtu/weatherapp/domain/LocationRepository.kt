package dk.dtu.weatherapp.domain

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.cityDataStore
import dk.dtu.weatherapp.cityIndex
import dk.dtu.weatherapp.getAppContext
import dk.dtu.weatherapp.models.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class LocationRepository(private val userId: String) {
    private val firestore = FirebaseFirestore.getInstance()

    private val mutableLocationFlow = MutableSharedFlow<List<Location>>()
    val locationFlow = mutableLocationFlow.asSharedFlow()

    private val mutableFavoriteLocationFlow = MutableSharedFlow<List<Location>>()
    val favoriteLocationFlow = mutableFavoriteLocationFlow.asSharedFlow()

    private val dataStore = getAppContext().cityDataStore // Assuming the DataStore is set up
    private val CITY_INDEX_KEY = stringPreferencesKey("city_index")

    suspend fun getLocations(input: String) = mutableLocationFlow.emit(
        searchCities(input)
    )

    suspend fun getFavoriteLocations() = mutableFavoriteLocationFlow.emit(
        fetchFavorites()
    )

    private suspend fun isCityIndexCached(): Boolean {
        val preferences = dataStore.data.first()
        return preferences.contains(CITY_INDEX_KEY)
    }

    // Function to load the cached city index
    private suspend fun loadCachedCityIndex() {
        val preferences = dataStore.data.first()
        val json = preferences[CITY_INDEX_KEY]
        if (!json.isNullOrEmpty()) {
            val type = object : TypeToken<Map<String, Location>>() {}.type
            val cachedIndex = Gson().fromJson<Map<String, Location>>(json, type)
            cityIndex.putAll(cachedIndex)
        }
    }

    // Function to cache the city index (after preprocessing)
    private suspend fun cacheCityIndex() {
        val json = Gson().toJson(cityIndex) // Serialize Map to JSON
        dataStore.edit { preferences ->
            preferences[CITY_INDEX_KEY] = json  // Store the entire JSON as a string
        }
    }

    suspend fun preprocessCities() {
        if (cityIndex.isNotEmpty()) {
            return
        }
        // Offload to IO dispatcher (background thread)
        withContext(Dispatchers.IO) {
            if (!isCityIndexCached()) {
                val inputStream = getAppContext().resources.openRawResource(R.raw.cities_all)

                inputStream.bufferedReader().use { reader ->
                    reader.lineSequence()
                        .drop(1) // Skip the first line (header)
                        .forEach { line ->
                            val columns = line.split(",")
                            if (columns.size > 1) {
                                val cityName = columns[1].trim() // Make sure to trim spaces around the name
                                cityIndex[cityName] = Location(name = cityName, 15.5, R.drawable.i01n)
                            }
                        }
                }
                cacheCityIndex()
            } else {
                loadCachedCityIndex()
            }
        }
    }

    fun searchCities(query: String): List<Location> {
        if (query.isBlank()) {
            return cityIndex.values.take(30).toList()
        }

        return cityIndex.filter { it.key.contains(query, ignoreCase = true) }
            .values
            .take(30) // Limit to 30 results
            .toList()
    }

    fun fetchFavorites(): List<Location> {
        try {
            val favoritesCollection = firestore.collection("users")
                .document(userId)
                .collection("favorites")

            var favorites: List<Location> = emptyList()

            favoritesCollection.get().addOnSuccessListener { result ->
                val favoriteCities = mutableListOf<Location>()
                for (document in result) {
                    val cityName = document.id
                    favoriteCities.add(Location(name = cityName, 15.5, R.drawable.i01n))
                }
                favorites = favoriteCities
            }
            return favorites
        } catch (e: Exception) {
            return emptyList()
        }
    }
}