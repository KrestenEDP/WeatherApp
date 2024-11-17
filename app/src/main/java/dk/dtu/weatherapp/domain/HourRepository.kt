package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.mock.MockHourDataSource
import dk.dtu.weatherapp.models.Hour
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class HourRepository {
    private val hourDataSource = MockHourDataSource()

    private val mutableHourlyFlow = MutableSharedFlow<List<Hour>>()
    val hourFlow = mutableHourlyFlow.asSharedFlow()

    suspend fun getHourlyForecast() = mutableHourlyFlow.emit(
        hourDataSource.getHourlyWeather()
    )
}