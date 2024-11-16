package dk.dtu.weatherapp.data.mock

import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.models.Day

class MockDayDataSource {
    private val data = listOf (
        Day(R.drawable.i01n, "Wednesday 16. January", 21, 14, 2.4),
        Day(R.drawable.i02n, "Thursday 17. January", 19, 13, 2.1),
        Day(R.drawable.i02d, "Friday 18. January", 17, 12, 2.0),
        Day(R.drawable.i13d, "Saturday 19. January", 24, 15, 5.2)
    )

    suspend fun getDailyWeather(): List<Day> {
        return data
    }
}