package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.model.StatsDao
import dk.dtu.weatherapp.data.remote.RemoteStatisticsDataSource
import dk.dtu.weatherapp.models.StatsDataObject
import dk.dtu.weatherapp.models.StatsDay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class StatsRepository {
    private val dataSource = RemoteStatisticsDataSource()

    private val mutableDayStatsFlow = MutableSharedFlow<StatsDay?>()
    val dayStatsFlow = mutableDayStatsFlow.asSharedFlow()

    private val mutableMonthStatsFlow = MutableSharedFlow<StatsDay?>()
    val monthStatsFlow = mutableMonthStatsFlow.asSharedFlow()

    private val mutableYearStatsFlow = MutableSharedFlow<List<StatsDay>?>()
    val yearStatsFlow = mutableYearStatsFlow.asSharedFlow()

    suspend fun getDayStats(day: Int, month: Int) = mutableDayStatsFlow.emit(
        dataSource.getDayStatistics(month = month, day = day)?.stats?.mapToStatsList()
    )

    suspend fun getMonthStats(month: Int) = mutableMonthStatsFlow.emit(
        dataSource.getMonthStatistics(month = month)?.stats?.mapToStatsList()
    )

    suspend fun getYearStats() = mutableYearStatsFlow.emit(
        dataSource.getYearStatistics()?.map { it.stats.mapToStatsList() }
    )
}

suspend fun StatsDao.mapToStatsList(): StatsDay {
    return StatsDay(
        month = month,
        temp = StatsDataObject(
            mean = convertTempUnit(temp.mean),
            max = convertTempUnit(temp.averageMax),
            min = convertTempUnit(temp.averageMin)
        ),
        pressure = StatsDataObject(
            mean = pressure.mean,
            max = pressure.max,
            min = pressure.min
        ),
        humidity = StatsDataObject(
            mean = humidity.mean,
            max = humidity.max,
            min = humidity.min
        ),
        wind = StatsDataObject(
            mean = convertSpeedUnit(wind.mean),
            max = convertSpeedUnit(wind.max),
            min = convertSpeedUnit(wind.min)
        ),
        precipitation = StatsDataObject(
            mean = convertPrecipitationUnit(precipitation.mean, 2),
            max = convertPrecipitationUnit(precipitation.max, 2),
            min = convertPrecipitationUnit(precipitation.min, 2)
        ),
        clouds = StatsDataObject(
            mean = clouds.mean,
            max = clouds.max,
            min = clouds.min
        ),
    )
}