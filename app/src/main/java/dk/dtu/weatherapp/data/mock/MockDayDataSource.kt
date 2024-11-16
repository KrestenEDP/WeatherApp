package dk.dtu.weatherapp.data.mock

import dk.dtu.weatherapp.models.Day
import kotlinx.coroutines.delay

class MockDayDataSource {
    private val data = listOf (
        Day("01d", "Wednesday 16. January", 21, 14, 2.4),
        Day("02n", "Thursday 17. January", 19, 13, 2.1),
        Day("11d", "Friday 18. January", 17, 12, 2.0),
        Day("50n", "Saturday 19. January", 24, 15, 5.2),
        Day("13d", "Sunday 20. January", 14, 11, 0.0),
        Day("13n", "Monday 21. January", 25, 12, 0.0),
        Day("02d", "Tuesday 22. January", 12, 13, 1.1),
        Day("01n", "Wednesday 23. January", 11, 13, 0.0)
    )

    suspend fun getDailyWeather(): List<Day> {
        return data
    }
}