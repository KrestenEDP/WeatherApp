package dk.dtu.weatherapp.domain

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dk.dtu.weatherapp.getAppContext
import dk.dtu.weatherapp.models.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val Context.currentLocationDataStore by preferencesDataStore(name = "current_location")

private val currentLocationDataStore = getAppContext().currentLocationDataStore
private val currentLocationNameKey = stringPreferencesKey("current_location_name")
private val currentLocationLatKey = stringPreferencesKey("current_location_lat")
private val currentLocationLonKey = stringPreferencesKey("current_location_lon")
private val customScope = CoroutineScope(Dispatchers.IO)
private var currentLocation: Location? = null

suspend fun fetchCurrentLocation() {
    currentLocation = currentLocationDataStore.data
        .map { preferences ->
            val location = Location(
                name = preferences[currentLocationNameKey] ?: "Kongens Lyngby",
                lat = preferences[currentLocationLatKey] ?: "55.77044",
                lon = preferences[currentLocationLonKey] ?: "12.50378",
                isFavorite = false
            )
            // TODO: Add to firebase if not already there, remove oldest element if more than max limit (15?)
            location
        }.first()
}

fun getCurrentLocation(): Location {
    if (currentLocation == null) {
        customScope.launch {
            fetchCurrentLocation()
        }
    }
    return currentLocation!!
}

fun setCurrentLocation(location: Location) {
    customScope.launch {
        currentLocationDataStore.edit { preferences ->
            preferences[currentLocationNameKey] = location.name
            preferences[currentLocationLatKey] = location.lat
            preferences[currentLocationLonKey] = location.lon
        }
    }
}