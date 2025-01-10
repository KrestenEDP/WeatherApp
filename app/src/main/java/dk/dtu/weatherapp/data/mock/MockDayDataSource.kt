package dk.dtu.weatherapp.data.mock

import dk.dtu.weatherapp.models.Day

class MockDayDataSource {
    private val data = listOf (
        Day("i01n", "Wednesday 16. January", 21.0, 14.0, 2.4, 5.0, 7.0, 180, 90, 1000, 80, "07:00", "16:00"),
        Day("i02n", "Thursday 17. January", 19.0, 13.0, 2.1, 4.0, 6.0, 190, 80, 1005, 85, "07:01", "16:01"),
        Day("i02d", "Friday 18. January", 17.0, 12.0, 2.0, 3.0, 5.0, 200, 70, 1010, 90, "07:02", "16:02"),
        Day("i13d", "Saturday 19. January", 24.0, 15.0, 5.2, 6.0, 8.0, 210, 60, 1015, 95, "07:03", "16:03"),
    )

    fun getDailyWeather(): List<Day> {
        return data
    }
}