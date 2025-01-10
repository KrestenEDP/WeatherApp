package dk.dtu.weatherapp.models

data class Current(
    val temp: Double,
    val chill: Double,
    val wind: Double,
    val precipitation: Double,
    val icon: String
)
