package dk.dtu.weatherapp.models

data class Hour(
    val time: String,
    val iconURL: String,
    val temp: Double,
    val rain: Double,
    val wind: Double,
    val windDegree: Int,
    val date: String = ""
)
