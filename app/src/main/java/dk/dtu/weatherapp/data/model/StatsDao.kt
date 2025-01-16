package dk.dtu.weatherapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatsDao(
    val month: Int,
    val temp: StatsTempDao,
    val pressure: StatsPressureDao,
    val humidity: StatsHumidityDao,
    val wind: StatsWindDao,
    val precipitation: StatsPrecipitationDao,
    val clouds: StatsCloudsDao
)

@Serializable
data class StatsTempDao(
    val mean: Double,
)

@Serializable
data class StatsPressureDao(
    val mean: Double,
)

@Serializable
data class StatsHumidityDao(
    val mean: Double,
)

@Serializable
data class StatsWindDao(
    val mean: Double,
)

@Serializable
data class StatsPrecipitationDao(
    val mean: Double,
)

@Serializable
data class StatsCloudsDao(
    val mean: Double,
)

@Serializable
data class StatsDataDao(
    @SerialName("result")
    val stats: StatsDao
)