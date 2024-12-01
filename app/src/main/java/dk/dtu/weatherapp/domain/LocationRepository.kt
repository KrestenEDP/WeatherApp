package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.mock.MockAlertDataSource
import dk.dtu.weatherapp.data.mock.MockLocationDataSource
import dk.dtu.weatherapp.models.Alert
import dk.dtu.weatherapp.models.Location
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class LocationRepository {
    private val currentDataSource = MockLocationDataSource()

    private val mutableAlertFlow = MutableSharedFlow<List<Location>>()
    val currentLocationFlow = mutableAlertFlow.asSharedFlow()

    suspend fun getLocations() = mutableAlertFlow.emit(
        currentDataSource.getLocations()
    )
}