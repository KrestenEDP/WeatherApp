package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.model.StatsDao
import dk.dtu.weatherapp.data.model.StatsListDao
import dk.dtu.weatherapp.data.remote.RemoteStatisticsDataSource
import dk.dtu.weatherapp.models.StatsDay

class StatsRepository {
    private val dataSource = RemoteStatisticsDataSource()

    suspend fun getDayStats(lat: Double, lon: Double, month: Int, day: Int): List<StatsDay> {
        val data = dataSource.getDayStatistics(lat = lat, lon = lon, month = month, day = day)
        val list: List<StatsDao> = arrayListOf(data.stats)
        return StatsListDao(list).mapToStatsList()
    }

    suspend fun getMonthStats(lat: Double, lon: Double, month: Int): List<StatsDay> {
        val data = dataSource.getMonthStatistics(lat = lat, lon = lon, month = month)
        return StatsListDao(arrayListOf(data.stats)).mapToStatsList()
    }

    suspend fun getYearStats(lat: Double, lon: Double): List<StatsDay> {
        return dataSource.getYearStatistics(lat = lat, lon = lon)
            .stats.mapToStatsList()
    }
}

fun StatsListDao.mapToStatsList(): List<StatsDay> {
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