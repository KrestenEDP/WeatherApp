package dk.dtu.weatherapp.ui.alerts

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.dtu.weatherapp.domain.AlertRepository
import dk.dtu.weatherapp.models.Alert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import android.content.Context
import dk.dtu.weatherapp.getAppContext

@SuppressLint("StaticFieldLeak")
class AlertsViewModel : ViewModel() {

    val context: Context = getAppContext()
    private val alertRepository = AlertRepository()
    private val mutableCurrent = MutableStateFlow<AlertsUIModel>(AlertsUIModel.Loading)
    val alertsUIState: StateFlow<AlertsUIModel> = mutableCurrent

    init {
        viewModelScope.launch {
            alertRepository.currentAlertFlow
                .collect { data ->
                    mutableCurrent.update {
                        if (data.isEmpty()) {
                            AlertsUIModel.Empty
                        } else {
                            AlertsUIModel.Data(data)
                        }
                    }

                    data.forEach { alert ->
                        showNotification(context, alert)
                    }
                }
        }

        getAlerts()
    }

    private fun getAlerts() = viewModelScope.launch {
        alertRepository.getAlerts()
    }
}

sealed class AlertsUIModel {
    data object Empty: AlertsUIModel()
    data object Loading: AlertsUIModel()
    data class Data(val alerts: List<Alert>) : AlertsUIModel()
}



