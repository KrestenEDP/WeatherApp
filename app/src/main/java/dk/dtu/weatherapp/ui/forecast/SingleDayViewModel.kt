package dk.dtu.weatherapp.ui.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.dtu.weatherapp.domain.HourlyFourDayForecastRepository
import dk.dtu.weatherapp.models.Hour
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SingleDayViewModel : ViewModel() {
    private val hourlyFourDayRepository = HourlyFourDayForecastRepository()

    private val fourDayMutable = MutableStateFlow<FourDayHourlyUIModel>(FourDayHourlyUIModel.Loading)
    val singleDayUIState: StateFlow<FourDayHourlyUIModel> = fourDayMutable

    init {
        viewModelScope.launch {
            hourlyFourDayRepository.fourDayForecastFlow
                .collect { data ->
                    fourDayMutable.update {
                        FourDayHourlyUIModel.Data(data)
                    }
                }
        }
        getHourlyForecast()
    }

    private fun getHourlyForecast() = viewModelScope.launch {
        hourlyFourDayRepository.getFourDayForecast()
    }
}

sealed class FourDayHourlyUIModel {
    data object Empty: FourDayHourlyUIModel()
    data object Loading: FourDayHourlyUIModel()
    data class Data(val fourDayHourly: List<List<Hour>>) : FourDayHourlyUIModel()
}


