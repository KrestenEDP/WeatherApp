package dk.dtu.weatherapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MainDataDao(
    val temp: Double,
    @SerialName("feels_like")
    val chill: Double
)

@Serializable
data class WindDao(
    val speed: Double,
    val deg: Int = 0
)

@Serializable
data class RainDao(
    @SerialName("1h")
    val amount: Double
)

@Serializable
data class SnowDao(
    @SerialName("1h")
    val amount: Double
)

@Serializable
data class WeatherDao(
    val icon: String
)

@Serializable
data class TempDao(
    val day: Double,
    val night: Double
)

@Serializable
data class WeatherHourDao(
    val weather: List<WeatherDao>,
    val main: MainDataDao,
    val wind: WindDao = WindDao(0.0),
    val rain: RainDao = RainDao(0.0),
    val snow: SnowDao = SnowDao(0.0),
    val dt_txt: String? = null
)

@Serializable
data class WeatherDayDao(
    val weather: List<WeatherDao>,
    val temp: TempDao,
    val rain: Double? = 0.0,
    val snow: Double? = 0.0,
    val dt: Long
)

@Serializable
data class HourlyWeatherDao(
    @SerialName("list")
    val hours: List<WeatherHourDao>
)

@Serializable
data class DailyWeatherDao(
    @SerialName("list")
    val days: List<WeatherDayDao>
)