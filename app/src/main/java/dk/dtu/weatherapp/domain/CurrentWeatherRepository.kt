package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.model.CurrentWeatherDao
import dk.dtu.weatherapp.data.remote.RemoteWeatherDataSource
import dk.dtu.weatherapp.models.Current
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class CurrentWeatherRepository {
    private val currentDataSource = RemoteWeatherDataSource()

    private val mutableCurrentWeatherFlow = MutableSharedFlow<Current>()
    val currentWeatherFlow = mutableCurrentWeatherFlow.asSharedFlow()

    suspend fun getCurrentWeather() = mutableCurrentWeatherFlow.emit(
        currentDataSource.getCurrentWeather().mapToCurrent()
    )
}

fun CurrentWeatherDao.mapToCurrent() = Current(
    temp = main.temp,
    chill = main.chill,
    wind = wind.speed,
    rain = rain.amount,
    icon = "i" + weather[0].icon, //icon
)