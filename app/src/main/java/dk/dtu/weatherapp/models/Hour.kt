package dk.dtu.weatherapp.models

data class Hour(
    val time: String,
    val iconURL: String,
    val temp: Int,
    val rain: Double,
    val wind: Int,
    val windDegree: Int
)