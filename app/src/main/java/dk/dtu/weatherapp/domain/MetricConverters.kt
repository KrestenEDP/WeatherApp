package dk.dtu.weatherapp.domain

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import dk.dtu.weatherapp.getAppContext
import dk.dtu.weatherapp.getUnitSetting
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Locale

suspend fun convertTempUnit(temp: Double): Double {
    return String.format(Locale.ROOT, "%.1f",
        when (getUnitSetting(getAppContext())) {
        0 -> kelvinToCelsius(temp)
        1 -> kelvinToFahrenheit(temp)
        else -> temp
        }
    ).toDouble()
}

suspend fun convertPrecipitationUnit(precipitation: Double): Double {
    return String.format(Locale.ROOT, "%.1f",
        when (getUnitSetting(getAppContext())) {
        0 -> precipitation
        1 -> mmToInches(precipitation)
        else -> precipitation
        }
    ).toDouble()
}

suspend fun convertSpeedUnit(speed: Double): Double {
    return String.format(Locale.ROOT, "%.1f",
        when (getUnitSetting(getAppContext())) {
        0 -> speed
        1 -> meterPerSecondsToMilesPerHour(speed)
        else -> speed
        }
    ).toDouble()
}

private fun kelvinToFahrenheit(kelvin: Double): Double {
    return (kelvinToCelsius(kelvin) * 9 / 5) + 32
}

private fun kelvinToCelsius(kelvin: Double): Double {
    return kelvin - 273.15
}

private fun mmToInches(mm: Double): Double {
    return mm / 25.4
}

private fun meterPerSecondsToMilesPerHour(ms: Double): Double {
    return ms * 2.236936
}

private suspend fun getUnitSetting(context: Context): Int {
    val dataStore = context.dataStore.data
    val preferredUnitKey = intPreferencesKey("preferred_unit")


    return dataStore.map { preferences ->
        preferences[preferredUnitKey] ?: 0
    }.first()
}