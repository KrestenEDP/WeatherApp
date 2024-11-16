package dk.dtu.weatherapp.models

data class Current(
    val temp: Int,
    val chill: Int,
    val wind: Int,
    val rain: Double,
    val icon: String
) {
}
