package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.model.AlertsParentDao
import dk.dtu.weatherapp.data.remote.RemoteAlertsDataSource
import dk.dtu.weatherapp.models.Alert
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

open class AlertRepository {
    private val currentDataSource = RemoteAlertsDataSource()

    private val mutableAlertFlow = MutableSharedFlow<List<Alert>?>()
    open val currentAlertFlow = mutableAlertFlow.asSharedFlow()

    open suspend fun getAlerts() = mutableAlertFlow.emit(
        currentDataSource.getAlerts()?.mapToAlerts()?.distinct()
    )
}

fun AlertsParentDao.mapToAlerts(): List<Alert> {
    return alertList.alerts.map {
        Alert(
            headline = it.headline,
            description = it.desc,
            severity = it.severity,
            urgency = it.urgency,
            event = it.event,
        )
    }
}