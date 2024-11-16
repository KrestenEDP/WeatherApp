package dk.dtu.weatherapp.models

data class Day(
    val iconURL: Int,
    val date: String,
    val dayTemp: Int,
    val nightTemp: Int,
    val rain: Double
)
