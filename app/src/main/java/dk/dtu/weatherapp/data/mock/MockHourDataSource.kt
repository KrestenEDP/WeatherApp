package dk.dtu.weatherapp.data.mock

import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.models.Hour

class MockHourDataSource {
    private val data = listOf (
        Hour("00:00", R.drawable.i01n, 15, 0.0, 5, 0),
        Hour("01:00", R.drawable.i01n, 14, 0.1, 6, 15),
        Hour("02:00", R.drawable.i01n, 13, 0.2, 7, 30),
        Hour("03:00", R.drawable.i01n, 12, 0.3, 8, 45),
        Hour("04:00", R.drawable.i01n, 11, 0.4, 9, 60),
        Hour("05:00", R.drawable.i01n, 10, 0.5, 10, 75),
        Hour("06:00", R.drawable.i01n, 9, 0.6, 11, 90),
        Hour("07:00", R.drawable.i01n, 8, 0.7, 12, 105),
        Hour("08:00", R.drawable.i01n, 7, 0.8, 13, 120),
        Hour("09:00", R.drawable.i01n, 6, 0.9, 14, 135),
        Hour("10:00", R.drawable.i01n, 5, 1.0, 15, 150),
        Hour("11:00", R.drawable.i01n, 4, 1.1, 16, 165),
        Hour("12:00", R.drawable.i01n, 3, 1.2, 17, 180),
        Hour("13:00", R.drawable.i01n, 2, 1.3, 18, 195),
        Hour("14:00", R.drawable.i01n, 1, 1.4, 19, 210),
        Hour("15:00", R.drawable.i01n, 0, 1.5, 20, 225),
        Hour("16:00", R.drawable.i01n, 1, 1.6, 21, 240),
        Hour("17:00", R.drawable.i01n, 2, 1.7, 22, 255),
        Hour("18:00", R.drawable.i01n, 3, 1.8, 23, 270),
        Hour("19:00", R.drawable.i01n, 4, 1.9, 24, 285),
        Hour("20:00", R.drawable.i01n, 5, 2.0, 25, 300),
        Hour("21:00", R.drawable.i01n, 6, 2.1, 26, 315),
        Hour("22:00", R.drawable.i01n, 7, 2.2, 27, 330),
        Hour("23:00", R.drawable.i01n, 8, 2.3, 28, 345),
        Hour("00:00", R.drawable.i01n, 9, 2.4, 29, 0),
        Hour("01:00", R.drawable.i01n, 10, 2.5, 30, 15),
        Hour("02:00", R.drawable.i01n, 11, 2.6, 31, 30),
        Hour("03:00", R.drawable.i01n, 12, 2.7, 32, 45),
        Hour("04:00", R.drawable.i01n, 13, 2.8, 33, 60),
        Hour("05:00", R.drawable.i01n, 14, 2.9, 34, 75),
        Hour("06:00", R.drawable.i01n, 15, 3.0, 35, 90),
        Hour("07:00", R.drawable.i01n, 16, 3.1, 36, 105),
        Hour("08:00", R.drawable.i01n, 17, 3.2, 37, 120),
        Hour("09:00", R.drawable.i01n, 18, 3.3, 38, 135),
        Hour("10:00", R.drawable.i01n, 19, 3.4, 39, 150),
        Hour("11:00", R.drawable.i01n, 20, 3.5, 40, 165),
        Hour("12:00", R.drawable.i01n, 21, 3.6, 41, 180),
        Hour("13:00", R.drawable.i01n, 22, 3.7, 42, 195),
        Hour("14:00", R.drawable.i01n, 23, 3.8, 43, 210),
        Hour("15:00", R.drawable.i01n, 24, 3.9, 44, 225),
        Hour("16:00", R.drawable.i01n, 25, 4.0, 45, 240),
        Hour("17:00", R.drawable.i01n, 26, 4.1, 46, 255),
        Hour("18:00", R.drawable.i01n, 27, 4.2, 47, 270),
        Hour("19:00", R.drawable.i01n, 28, 4.3, 48, 285),
        Hour("20:00", R.drawable.i01n, 29, 4.4, 49, 300),
        Hour("21:00", R.drawable.i01n, 30, 4.5, 50, 315),
        Hour("22:00", R.drawable.i01n, 31, 4.6, 51, 330),
        Hour("23:00", R.drawable.i01n, 32, 4.7, 52, 345)
    )

    fun getHourlyWeather(): List<Hour> {
        return data
    }
}