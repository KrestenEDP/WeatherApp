package dk.dtu.weatherapp.data.mock

import dk.dtu.weatherapp.models.Current

class MockCurrentWeatherDataSource {
    private val data = Current(17, 14, 12, 2.4, "01d")

    fun getCurrentWeather(): Current {
        return data
    }
}