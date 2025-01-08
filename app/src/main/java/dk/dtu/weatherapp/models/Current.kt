package dk.dtu.weatherapp.models

data class Current(
    val temp: Double,
    val chill: Double,
    val wind: Double,
    val rain: Double,
    val icon: String
)
