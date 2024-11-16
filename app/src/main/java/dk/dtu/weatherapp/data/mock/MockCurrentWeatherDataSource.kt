package dk.dtu.weatherapp.data.mock

import dk.dtu.weatherapp.models.Current
import kotlinx.coroutines.delay

class MockCurrentWeatherDataSource {
    private val data = Current(17, 14, 12, 2.4, "01d")

    suspend fun getCurrentWeather(): Current {
        delay(4000)
        return data
    }
}