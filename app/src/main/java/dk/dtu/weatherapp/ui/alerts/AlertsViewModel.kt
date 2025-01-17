package dk.dtu.weatherapp.ui.alerts

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.dtu.weatherapp.domain.AlertRepository
import dk.dtu.weatherapp.getAppContext
import dk.dtu.weatherapp.models.Alert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class AlertsViewModel(
    private val alertRepository: AlertRepository = AlertRepository()
) : ViewModel() {

    val context: Context = getAppContext()
    private val mutableCurrent = MutableStateFlow<AlertsUIModel>(AlertsUIModel.Loading)
    val alertsUIState: StateFlow<AlertsUIModel> = mutableCurrent

    init {
        viewModelScope.launch {
            alertRepository.currentAlertFlow
                .collect { data ->
                    mutableCurrent.update {
                        if (data == null) {
                            AlertsUIModel.RequestError
                        } else if (data.isEmpty()) {
                            AlertsUIModel.Empty
                        } else {
                            AlertsUIModel.Data(data)
                        }
                    }

                    data?.forEach { alert ->
                        showNotification(context, alert)
                    }
                }
        }

        getAlerts()
    }

    fun getAlerts() = viewModelScope.launch {
        alertRepository.getAlerts()
    }
}

sealed class AlertsUIModel {
    data object RequestError: AlertsUIModel()
    data object Empty: AlertsUIModel()
    data object Loading: AlertsUIModel()
    data class Data(val alerts: List<Alert>) : AlertsUIModel()
}



