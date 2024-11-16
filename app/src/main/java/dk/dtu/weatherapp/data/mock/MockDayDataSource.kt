package dk.dtu.weatherapp.data.mock

import dk.dtu.weatherapp.models.Day
import kotlinx.coroutines.delay

class MockDayDataSource {
    private val data = listOf (
        Day("01d", "Wednesday 16. January", 21, 14, 2.4),
        Day("02n", "Thursday 17. January", 19, 13, 2.1),
        Day("11d", "Friday 18. January", 17, 12, 2.0),
        Day("50n", "Saturday 19. January", 24, 15, 5.2)
    )

    suspend fun getDailyWeather(): List<Day> {
        return data
    }
}