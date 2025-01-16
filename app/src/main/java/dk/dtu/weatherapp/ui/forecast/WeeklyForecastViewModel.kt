package dk.dtu.weatherapp.ui.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.dtu.weatherapp.domain.DayRepository
import dk.dtu.weatherapp.domain.HourlyFourDayForecastRepository
import dk.dtu.weatherapp.models.Day
import dk.dtu.weatherapp.models.Hour
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeeklyForecastViewModel : ViewModel() {
    private val hourlyRepository = HourlyFourDayForecastRepository()
    private val dayRepository = DayRepository()

    private val hourlyMutable = MutableStateFlow<HourlyUIModel>(HourlyUIModel.Loading)
    val hourlyUIState: StateFlow<HourlyUIModel> = hourlyMutable

    private val dayMutable = MutableStateFlow<DayUIModel>(DayUIModel.Loading)
    val dayUIState: StateFlow<DayUIModel> = dayMutable

    init {
        viewModelScope.launch {
            hourlyRepository.fourDayForecastFlow
                .collect { data ->
                    hourlyMutable.update {
                        if (data == null) HourlyUIModel.RequestError
                        else if (data.isEmpty()) HourlyUIModel.Empty
                        else HourlyUIModel.Data(data)
                    }
                }
        }
        viewModelScope.launch {
            dayRepository.dayFlow
                .collect { data ->
                    dayMutable.update {
                        if (data == null) DayUIModel.RequestError
                        else if (data.isEmpty()) DayUIModel.Empty
                        else DayUIModel.Data(data)
                    }
                }
        }
        getHourlyForecast()
        getDailyForecast()
    }

    private fun getHourlyForecast() = viewModelScope.launch {
        hourlyRepository.getFourDayForecast()
    }
    private fun getDailyForecast() = viewModelScope.launch {
        dayRepository.getDailyForecast()
    }
}

sealed class HourlyUIModel {
    data object RequestError: HourlyUIModel()
    data object Empty: HourlyUIModel()
    data object Loading: HourlyUIModel()
    data class Data(val hours: List<List<Hour>>) : HourlyUIModel()
}

sealed class DayUIModel {
    data object RequestError: DayUIModel()
    data object Empty: DayUIModel()
    data object Loading: DayUIModel()
    data class Data(val days: List<Day>) : DayUIModel()
}


