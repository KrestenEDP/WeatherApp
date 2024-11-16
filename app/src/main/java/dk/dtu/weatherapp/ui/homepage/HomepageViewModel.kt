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

    private lateinit var current: Current
    private lateinit var daily: List<Day>
    private lateinit var hourly: List<Hour>

    init {
        viewModelScope.launch {
            currentWeatherRepository.currentWeatherFlow
                .collect { data ->
                    current = data
                    update()
                }
        }
        viewModelScope.launch {
            dayRepository.dayFlow
                .collect { data ->
                    daily = data
                    update()
                }
        }
        viewModelScope.launch {
            hourRepository.hourFlow
                .collect { data ->
                    hourly = data
                    update()
                }
        }

        getCurrentWeather()
        getHourlyWeather()
        getDailyWeather()
    }

    private fun update() {
        if (this::current.isInitialized
            && this::daily.isInitialized
            && this::hourly.isInitialized) {
            weatherMutableCurrent.update {
                WeatherUIModel.Data(current, daily, hourly)
            }
        }
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
    data class Data(val currentWeather: Current, val daily: List<Day>, val hourly: List<Hour>) : WeatherUIModel()
}



