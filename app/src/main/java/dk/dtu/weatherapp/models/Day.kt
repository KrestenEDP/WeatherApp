package dk.dtu.weatherapp.models

data class Day(
    val iconURL: String,
    val date: String,
    val dayTemp: Double,
    val nightTemp: Double,
    val precipitation: Double
)
