package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.mock.MockCurrentWeatherDataSource
import dk.dtu.weatherapp.models.Current
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class CurrentWeatherRepository {
    private val currentDataSource = MockCurrentWeatherDataSource()

    private val mutableCurrentWeatherFlow = MutableSharedFlow<Current>()
    val currentWeatherFlow = mutableCurrentWeatherFlow.asSharedFlow()

    suspend fun getCurrentWeather() = mutableCurrentWeatherFlow.emit(
        currentDataSource.getCurrentWeather()
    )
}