package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.mock.MockHourlyFourDayForecast
import dk.dtu.weatherapp.models.Hour
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class HourlyFourDayForecastRepository {
    private val currentDataSource = MockHourlyFourDayForecast()

    private val mutableHourlyForecastFlow = MutableSharedFlow<List<Hour>>()
    val fourDayForecastFlow = mutableHourlyForecastFlow.asSharedFlow()

    suspend fun getFourDayForecast() = mutableHourlyForecastFlow.emit(
        currentDataSource.getFourDayHourlyForecast()
    )
}