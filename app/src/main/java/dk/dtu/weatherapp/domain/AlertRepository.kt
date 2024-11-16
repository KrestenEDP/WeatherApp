package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.mock.MockAlertDataSource
import dk.dtu.weatherapp.data.mock.MockCurrentWeatherDataSource
import dk.dtu.weatherapp.models.Alert
import dk.dtu.weatherapp.models.Current
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AlertRepository {
    private val currentDataSource = MockAlertDataSource()

    private val mutableAlertFlow = MutableSharedFlow<List<Alert>>()
    val currentAlertFlow = mutableAlertFlow.asSharedFlow()

    suspend fun getAlerts() = mutableAlertFlow.emit(
        currentDataSource.getAlerts()
    )
}