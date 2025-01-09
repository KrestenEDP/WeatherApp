package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.model.StatsListDao
import dk.dtu.weatherapp.data.remote.RemoteStatisticsDataSource
import dk.dtu.weatherapp.models.StatsDay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class StatsRepository {
    private val dataSource = RemoteStatisticsDataSource()

    private val mutableStatsFlow = MutableSharedFlow<List<StatsDay>>()
    val currentStatsFlow = mutableStatsFlow.asSharedFlow()

    suspend fun getDayStats(
        lat: Double,
        lon: Double,
        month: Int,
        day: Int
        ) = mutableStatsFlow.emit(
        dataSource.getDayStatistics(
            lat = lat,
            lon = lon,
            month = month,
            day = day
        ).mapToStatsDay()
    )

    suspend fun getMonthStats(
        lat: Double,
        lon: Double,
        month: Int
        ) = mutableStatsFlow.emit(
        dataSource.getMonthStatistics(
            lat = lat,
            lon = lon,
            month = month
        ).mapToStatsDay()
    )

    suspend fun getYearStats(
        lat: Double,
        lon: Double
        ) = mutableStatsFlow.emit(
        dataSource.getYearStatistics(
            lat = lat,
            lon = lon
        ).mapToStatsDay()
    )
}

fun StatsListDao.mapToStatsDay(): List<StatsDay> {
    return stats.map {
        StatsDay(
            month = it.month,
            day = it.day,
            temp = it.temp,
            pressure = it.pressure,
            humidity = it.humidity,
            wind = it.wind,
            precipitation = it.precipitation,
            clouds = it.clouds
        )
    }
}