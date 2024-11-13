package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.mock.MockDayDataSource
import dk.dtu.weatherapp.models.Day
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class DayRepository {
    private val dayDataSource = MockDayDataSource()

    private val mutableDayFlow = MutableSharedFlow<List<Day>>()
    val dayFlow = mutableDayFlow.asSharedFlow()

    suspend fun getDailyForecast() = mutableDayFlow.emit(
        dayDataSource.getDailyWeather()
    )
}