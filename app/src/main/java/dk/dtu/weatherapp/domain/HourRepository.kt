package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.model.HourlyWeatherDao
import dk.dtu.weatherapp.data.remote.RemoteWeatherDataSource
import dk.dtu.weatherapp.models.Hour
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.Locale

class HourRepository {
    private val hourDataSource = RemoteWeatherDataSource()

    private val mutableHourlyFlow = MutableSharedFlow<List<Hour>>()
    val hourFlow = mutableHourlyFlow.asSharedFlow()

    suspend fun getHourlyForecast() = mutableHourlyFlow.emit(
        hourDataSource.getHourlyWeatherToday().mapToHours()
    )
}

fun HourlyWeatherDao.mapToHours(): List<Hour> {
    return hours.map {
        Hour(
            time = it.dt_txt?.split(" ")[1]?.substring(0,5) ?: "",
            iconURL = "i" + it.weather[0].icon,
            temp = String.format(Locale.ROOT, "%.1f", it.main.temp - 273.15).toDouble(),
            rain = it.rain.amount,
            wind = String.format(Locale.ROOT, "%.1f", it.wind.speed).toDouble(),
            windDegree = it.wind.deg
        )
    }
}