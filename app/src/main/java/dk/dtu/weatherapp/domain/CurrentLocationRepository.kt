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

val Context.currentLocationDataStore by preferencesDataStore(name = "current_location")

private val currentLocationDataStore = getAppContext().currentLocationDataStore
private val currentLocationNameKey = stringPreferencesKey("current_location_name")
private val currentLocationLatKey = stringPreferencesKey("current_location_lat")
private val currentLocationLonKey = stringPreferencesKey("current_location_lon")
private val customScope = CoroutineScope(Dispatchers.IO)
private var currentLocation: Location? = null

suspend fun fetchCurrentLocation() {
    currentLocation = currentLocationDataStore.data
        .map { preferences ->
            Location(
                name = preferences[currentLocationNameKey] ?: "Kongens Lyngby",
                lat = preferences[currentLocationLatKey] ?: "55.77044",
                lon = preferences[currentLocationLonKey] ?: "12.50378",
                isFavorite = false
            )
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