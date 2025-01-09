package dk.dtu.weatherapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class StatsDao(
    val month: Int,
    val day: Int,
    val temp: List<Double>,
    val pressure: List<Double>,
    val humidity: List<Double>,
    val wind: List<Double>,
    val precipitation: List<Double>,
    val clouds: List<Double>
)

@Serializable
data class StatsListDao(
    val stats: List<StatsDao>
)