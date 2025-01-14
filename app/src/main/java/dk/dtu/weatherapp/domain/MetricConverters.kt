package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.getAppContext
import dk.dtu.weatherapp.getUnitSetting
import java.util.Locale

suspend fun convertTempUnit(temp: Double): Double {
    return String.format(Locale.ENGLISH, "%.1f",
        when (getUnitSetting(getAppContext())) {
        0 -> kelvinToCelsius(temp)
        1 -> kelvinToFahrenheit(temp)
        else -> temp
        }
    ).toDouble()
}

suspend fun convertPrecipitationUnit(precipitation: Double): Double {
    return String.format(Locale.ENGLISH, "%.1f",
        when (getUnitSetting(getAppContext())) {
        0 -> precipitation
        1 -> mmToInches(precipitation)
        else -> precipitation
        }
    ).toDouble()
}

suspend fun convertSpeedUnit(speed: Double): Double {
    return String.format(Locale.ENGLISH, "%.1f",
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