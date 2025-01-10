package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.model.WeatherHourDao
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

suspend fun WeatherHourDao.mapToCurrent() = Current(
    temp = convertTempUnit(main.temp),
    chill = convertTempUnit(main.chill),
    wind = convertSpeedUnit(wind.speed),
    precipitation = convertPrecipitationUnit(rain.amount + snow.amount),
    icon = "i" + weather[0].icon, //icon
)