package dk.dtu.weatherapp.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.dtu.weatherapp.domain.LocationRepository
import dk.dtu.weatherapp.models.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationsViewModel : ViewModel() {
    private val locationRepository = LocationRepository()
    private val mutableCurrent = MutableStateFlow<LocationsUIModel>(LocationsUIModel.Loading)
    val locationsUIState: StateFlow<LocationsUIModel> = mutableCurrent

    init {
        viewModelScope.launch {
            locationRepository.currentLocationFlow
                .collect { data ->
                    mutableCurrent.update {
                        LocationsUIModel.Data(data)
                    }
                }
        }

        getLocations()
    }

    private fun getLocations() = viewModelScope.launch {
        locationRepository.getLocations()
    }
}

sealed class LocationsUIModel {
    data object Empty: LocationsUIModel()
    data object Loading: LocationsUIModel()
    data class Data(val locations: List<Location>) : LocationsUIModel()
}



