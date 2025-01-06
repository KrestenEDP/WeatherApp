package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.model.WeatherHourDao
import dk.dtu.weatherapp.data.remote.RemoteWeatherDataSource
import dk.dtu.weatherapp.models.Current
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.Locale

class CurrentWeatherRepository {
    private val currentDataSource = RemoteWeatherDataSource()

    private val mutableCurrentWeatherFlow = MutableSharedFlow<Current>()
    val currentWeatherFlow = mutableCurrentWeatherFlow.asSharedFlow()

    suspend fun getCurrentWeather() = mutableCurrentWeatherFlow.emit(
        currentDataSource.getCurrentWeather().mapToCurrent()
    )
}

fun WeatherHourDao.mapToCurrent() = Current(
    temp = String.format(Locale.ROOT, "%.2f", main.temp - 273.15).toDouble(),
    chill = String.format(Locale.ROOT, "%.2f", main.chill - 273.15).toDouble(),
    wind = String.format(Locale.ROOT, "%.1f", wind.speed).toDouble(),
    rain = rain.amount,
    icon = "i" + weather[0].icon, //icon
)