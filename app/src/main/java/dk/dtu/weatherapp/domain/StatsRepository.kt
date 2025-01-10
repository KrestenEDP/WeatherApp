package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.model.StatsDataDao
import dk.dtu.weatherapp.data.model.StatsListDao
import dk.dtu.weatherapp.data.remote.RemoteStatisticsDataSource
import dk.dtu.weatherapp.models.StatsDay

class StatsRepository {
    private val dataSource = RemoteStatisticsDataSource()

    suspend fun getDayStats(lat: Double, lon: Double, month: Int, day: Int): StatsDay {
        val data: StatsDataDao = dataSource.getDayStatistics(lat = lat, lon = lon, month = month, day = day)
        return StatsDay(
            month = data.stats.month,
            day = data.stats.day,
            temp = data.stats.temp,
            pressure = data.stats.pressure,
            humidity = data.stats.humidity,
            wind = data.stats.wind,
            precipitation = data.stats.precipitation,
            clouds = data.stats.clouds
        )
    }

    suspend fun getMonthStats(lat: Double, lon: Double, month: Int): StatsDay {
        val data = dataSource.getMonthStatistics(lat = lat, lon = lon, month = month)
        return StatsDay(
            month = data.stats.month,
            day = data.stats.day,
            temp = data.stats.temp,
            pressure = data.stats.pressure,
            humidity = data.stats.humidity,
            wind = data.stats.wind,
            precipitation = data.stats.precipitation,
            clouds = data.stats.clouds
        )
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