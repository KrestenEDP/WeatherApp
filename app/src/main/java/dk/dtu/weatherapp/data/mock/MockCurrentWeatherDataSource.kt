package dk.dtu.weatherapp.data.mock

import dk.dtu.weatherapp.models.Current

class MockCurrentWeatherDataSource {
    private val data = Current(17.0, 14.0, 12.0, 2.4, "i01n")

    fun getCurrentWeather(): Current {
        return data
    }
}