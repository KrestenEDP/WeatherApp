package dk.dtu.weatherapp.ui.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.dtu.weatherapp.domain.CurrentWeatherRepository
import dk.dtu.weatherapp.domain.DayRepository
import dk.dtu.weatherapp.domain.HourRepository
import dk.dtu.weatherapp.models.Current
import dk.dtu.weatherapp.models.Day
import dk.dtu.weatherapp.models.Hour
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomepageViewModel : ViewModel() {
    private val currentWeatherRepository = CurrentWeatherRepository()
    private val dayRepository = DayRepository()
    private val hourRepository = HourRepository()

    private val weatherMutableCurrent = MutableStateFlow<WeatherUIModel>(WeatherUIModel.Loading)
    val weatherUIState: StateFlow<WeatherUIModel> = weatherMutableCurrent
    private val dailyMutableCurrent = MutableStateFlow<DailyUIModel>(DailyUIModel.Loading)
    val dailyUIState: StateFlow<DailyUIModel> = dailyMutableCurrent
    private val hourlyMutableCurrent = MutableStateFlow<HourlyUIModel>(HourlyUIModel.Loading)
    val hourlyUIState: StateFlow<HourlyUIModel> = hourlyMutableCurrent

    init {
        viewModelScope.launch {
            currentWeatherRepository.currentWeatherFlow
                .collect { data ->
                    weatherMutableCurrent.update {
                        WeatherUIModel.Data(data)
                    }
                }
        }
        viewModelScope.launch {
            dayRepository.dayFlow
                .collect { data ->
                    dailyMutableCurrent.update {
                        DailyUIModel.Data(data)
                    }
                }
        }
        viewModelScope.launch {
            hourRepository.hourFlow
                .collect { data ->
                    hourlyMutableCurrent.update {
                        HourlyUIModel.Data(data)
                    }
                }
        }

        getCurrentWeather()
        getHourlyWeather()
        getDailyWeather()
    }

    private fun getCurrentWeather() = viewModelScope.launch {
        currentWeatherRepository.getCurrentWeather()
    }
    private fun getHourlyWeather() = viewModelScope.launch {
        dayRepository.getDailyForecast()
    }
    private fun getDailyWeather() = viewModelScope.launch {
        hourRepository.getHourlyForecast()
    }
}

sealed class WeatherUIModel {
    data object Empty: WeatherUIModel()
    data object Loading: WeatherUIModel()
    data class Data(val currentWeather: Current) : WeatherUIModel()
}

sealed class DailyUIModel {
    data object Empty: DailyUIModel()
    data object Loading: DailyUIModel()
    data class Data(val daily: List<Day>) : DailyUIModel()
}

sealed class HourlyUIModel {
    data object Empty: HourlyUIModel()
    data object Loading: HourlyUIModel()
    data class Data(val hourly: List<Hour>) : HourlyUIModel()
}



