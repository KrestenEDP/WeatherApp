package dk.dtu.weatherapp.ui.alerts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.dtu.weatherapp.domain.AlertRepository
import dk.dtu.weatherapp.domain.CurrentWeatherRepository
import dk.dtu.weatherapp.domain.DayRepository
import dk.dtu.weatherapp.domain.HourRepository
import dk.dtu.weatherapp.models.Alert
import dk.dtu.weatherapp.models.Current
import dk.dtu.weatherapp.models.Day
import dk.dtu.weatherapp.models.Hour
import dk.dtu.weatherapp.ui.homepage.DailyUIModel
import dk.dtu.weatherapp.ui.homepage.HourlyUIModel
import dk.dtu.weatherapp.ui.homepage.WeatherUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AlertsViewModel : ViewModel() {
    private val alertRepository = AlertRepository()
    private val mutableCurrent = MutableStateFlow<AlertsUIModel>(AlertsUIModel.Loading)
    val alertsUIState: StateFlow<AlertsUIModel> = mutableCurrent

    init {
        viewModelScope.launch {
            alertRepository.currentAlertFlow
                .collect { data ->
                    mutableCurrent.update {
                        AlertsUIModel.Data(data)
                    }
                }
        }

        getCurrentWeather()
    }

    private fun getCurrentWeather() = viewModelScope.launch {
        alertRepository.getAlerts()
    }
}

sealed class AlertsUIModel {
    data object Empty: AlertsUIModel()
    data object Loading: AlertsUIModel()
    data class Data(val alerts: List<Alert>) : AlertsUIModel()
}



