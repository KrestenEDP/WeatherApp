package dk.dtu.weatherapp.data.mock

import dk.dtu.weatherapp.models.Day

class MockDayDataSource {
    private val data = listOf (
        Day("i01n", "Wednesday 16. January", 21.0, 14.0, 2.4),
        Day("i02n", "Thursday 17. January", 19.0, 13.0, 2.1),
        Day("i02d", "Friday 18. January", 17.0, 12.0, 2.0),
        Day("i13d", "Saturday 19. January", 24.0, 15.0, 5.2)
    )

    fun getDailyWeather(): List<Day> {
        return data
    }
}