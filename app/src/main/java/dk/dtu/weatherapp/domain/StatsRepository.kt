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

    suspend fun getDayStats(day: Int, month: Int): StatsDay {
        val data = dataSource.getDayStatistics(month = month, day = day)
        return data.stats.mapToStatsList()
    }

    suspend fun getMonthStats(month: Int): StatsDay {
        val data = dataSource.getMonthStatistics(month = month)
        return data.stats.mapToStatsList()
    }

    suspend fun getYearStats(): List<StatsDay> {
        return dataSource.getYearStatistics()
            .stats.stats.map { it.mapToStatsList() }
    }
}

suspend fun StatsDao.mapToStatsList(): StatsDay {
    return StatsDay(
        month = month,
        temp = StatsDayTemp(mean = convertTempUnit(temp.mean)),
        pressure = StatsDayPressure(mean = pressure.mean),
        humidity = StatsDayHumidity(mean = humidity.mean),
        wind = StatsDayWind(mean = convertSpeedUnit(wind.mean)),
        precipitation = StatsDayPrecipitation(mean = convertPrecipitationUnit(precipitation.mean)),
        clouds = StatsDayClouds(mean = clouds.mean)
    )
}