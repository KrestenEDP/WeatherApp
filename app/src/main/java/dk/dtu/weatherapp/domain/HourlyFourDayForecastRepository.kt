package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.remote.RemoteWeatherDataSource
import dk.dtu.weatherapp.models.Hour
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class HourlyFourDayForecastRepository {
    private val currentDataSource = RemoteWeatherDataSource()

    private val mutableHourlyForecastFlow = MutableSharedFlow<List<List<Hour>>?>()
    val fourDayForecastFlow = mutableHourlyForecastFlow.asSharedFlow()

    suspend fun getFourDayForecast() = mutableHourlyForecastFlow.emit(
        splitHoursIntoDays(currentDataSource.getHourlyWeather()?.mapToHours())
    )
}

fun splitHoursIntoDays(hoursList: List<Hour>?): List<List<Hour>>? {
    if (hoursList == null) return null
    val days = mutableListOf<List<Hour>>()

    val startHour = hoursList[0].time.split(":")[0].toInt()
    val firstDay = hoursList.slice(0 until 24 - startHour)
    val remainingHours = hoursList.slice(24 - startHour until hoursList.size)

    days.add(firstDay)
    days += remainingHours.chunked(24)
    return days
}