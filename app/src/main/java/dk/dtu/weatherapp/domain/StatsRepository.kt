package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.model.StatsDao
import dk.dtu.weatherapp.data.remote.RemoteStatisticsDataSource
import dk.dtu.weatherapp.models.StatsDay
import dk.dtu.weatherapp.models.StatsDayClouds
import dk.dtu.weatherapp.models.StatsDayHumidity
import dk.dtu.weatherapp.models.StatsDayPrecipitation
import dk.dtu.weatherapp.models.StatsDayPressure
import dk.dtu.weatherapp.models.StatsDayTemp
import dk.dtu.weatherapp.models.StatsDayWind

class StatsRepository {
    private val dataSource = RemoteStatisticsDataSource()

    suspend fun getDayStats(lat: Double, lon: Double, month: Int, day: Int): StatsDay {
        val data = dataSource.getDayStatistics(lat = lat, lon = lon, month = month, day = day)
        return data.stats.mapToStatsList()
    }

    suspend fun getMonthStats(lat: Double, lon: Double, month: Int): StatsDay {
        val data = dataSource.getMonthStatistics(lat = lat, lon = lon, month = month)
        return data.stats.mapToStatsList()
    }

    suspend fun getYearStats(lat: Double, lon: Double): List<StatsDay> {
        return dataSource.getYearStatistics(lat = lat, lon = lon)
            .stats.stats.map { it.mapToStatsList() }
    }
}

fun StatsDao.mapToStatsList(): StatsDay {
    return StatsDay(
        month = month,
        day = day,
        temp = StatsDayTemp(mean = temp.mean),
        pressure = StatsDayPressure(mean = pressure.mean),
        humidity = StatsDayHumidity(mean = humidity.mean),
        wind = StatsDayWind(mean = wind.mean),
        precipitation = StatsDayPrecipitation(mean = precipitation.mean),
        clouds = StatsDayClouds(mean = clouds.mean)
    )
}