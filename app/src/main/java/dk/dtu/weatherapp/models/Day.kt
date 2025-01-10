package dk.dtu.weatherapp.models

data class Day(
    val iconURL: String,
    val date: String,
    val dayTemp: Double,
    val nightTemp: Double,
    val precipitation: Double,
    val windSpeed: Double,
    val windGusts: Double,
    val windDirection: Int,
    val cloudiness: Int,
    val pressure: Int,
    val humidity: Int,
    val sunrise: String,
    val sunset: String,
)
