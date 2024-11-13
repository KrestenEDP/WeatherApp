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
}
