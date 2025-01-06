package dk.dtu.weatherapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Main(
    val temp: Double,
    @SerialName("feels_like")
    val chill: Double
)

@Serializable
data class Wind(
    val speed: Double
)

@Serializable
data class Rain(
    @SerialName("1h")
    val amount: Double
)

@Serializable
data class WeatherHour(
    val icon: String
)

@Serializable
data class CurrentWeatherDao(
    val weather: List<WeatherHour>,
    val main: Main,
    val wind: Wind = Wind(0.0),
    val rain: Rain = Rain(0.0),
)