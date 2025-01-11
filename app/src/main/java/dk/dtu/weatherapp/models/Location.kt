package dk.dtu.weatherapp.models

data class Location(
    val name: String,
    val lat: String,
    val lon: String,
    var isFavorite: Boolean
    //val temperature: Double,
    //val iconURL: Int,
)
