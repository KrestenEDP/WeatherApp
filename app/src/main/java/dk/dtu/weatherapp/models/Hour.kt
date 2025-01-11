package dk.dtu.weatherapp.models

data class Hour(
    val time: String,
    val iconURL: String,
    val temp: Double,
    val precipitation: Double,
    val wind: Double,
    val windDegree: Int,
)
