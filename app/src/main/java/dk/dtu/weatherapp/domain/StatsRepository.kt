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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class StatsRepository {
    private val dataSource = RemoteStatisticsDataSource()

    private val mutableDayStatsFlow = MutableSharedFlow<StatsDay?>()
    val dayStatsFlow = mutableDayStatsFlow.asSharedFlow()

    private val mutableMonthStatsFlow = MutableSharedFlow<StatsDay?>()
    val monthStatsFlow = mutableMonthStatsFlow.asSharedFlow()

    private val mutableYearStatsFlow = MutableSharedFlow<List<StatsDay>>()
    val yearStatsFlow = mutableYearStatsFlow.asSharedFlow()

    suspend fun getDayStats(day: Int, month: Int) = mutableDayStatsFlow.emit(
        dataSource.getDayStatistics(month = month, day = day)?.stats?.mapToStatsList()
    )

    suspend fun getMonthStats(month: Int) = mutableMonthStatsFlow.emit(
        dataSource.getMonthStatistics(month = month)?.stats?.mapToStatsList()
    )

    suspend fun getYearStats() = mutableYearStatsFlow.emit(
        dataSource.getYearStatistics()?.stats?.stats?.map { it.mapToStatsList() } ?: emptyList()
    )
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