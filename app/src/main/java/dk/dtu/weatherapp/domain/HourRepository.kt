package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.model.HourlyWeatherDao
import dk.dtu.weatherapp.data.remote.RemoteWeatherDataSource
import dk.dtu.weatherapp.models.Hour
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class HourRepository {
    private val hourDataSource = RemoteWeatherDataSource()

    private val mutableHourlyFlow = MutableSharedFlow<List<Hour>?>()
    val hourFlow = mutableHourlyFlow.asSharedFlow()

    suspend fun getHourlyForecast() = mutableHourlyFlow.emit(
        hourDataSource.getHourlyWeatherToday(count = 12)?.mapToHours()
    )
}

suspend fun HourlyWeatherDao.mapToHours(): List<Hour> {
    return hours.map {
        Hour(
            time = it.dt_txt?.split(" ")[1]?.substring(0,5) ?: "",
            iconURL = "i" + it.weather[0].icon,
            temp = convertTempUnit(it.main.temp),
            precipitation = convertPrecipitationUnit(it.rain.amount + it.snow.amount),
            wind = convertSpeedUnit(it.wind.speed),
            windDegree = it.wind.deg
        )
    }
}