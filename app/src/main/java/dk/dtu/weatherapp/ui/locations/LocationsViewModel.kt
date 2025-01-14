package dk.dtu.weatherapp.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.dtu.weatherapp.domain.LocationRepository
import dk.dtu.weatherapp.models.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationsViewModel(userId: String) : ViewModel() {
    private val locationRepository = LocationRepository(userId)

    private val locationMutable = MutableStateFlow<LocationsUIModel>(LocationsUIModel.Loading)
    val locationsUIState: StateFlow<LocationsUIModel> = locationMutable

    private val favoriteLocationMutable = MutableStateFlow<LocationsUIModel>(LocationsUIModel.Loading)
    val favoriteLocationsUIState: StateFlow<LocationsUIModel> = favoriteLocationMutable

    private val recentLocationMutable = MutableStateFlow<LocationsUIModel>(LocationsUIModel.Loading)
    val recentLocationsUIState: StateFlow<LocationsUIModel> = recentLocationMutable

    private var currentJob: Job? = null

    init {
        viewModelScope.launch {
            locationRepository.locationFlow
                .collect { data ->
                    locationMutable.update {
                        if (data.isEmpty()) {
                            LocationsUIModel.Empty
                        } else {
                            LocationsUIModel.Data(data)
                        }
                    }
                }
        }
        viewModelScope.launch {
            locationRepository.favoriteLocationFlow
                .collect { data ->
                    favoriteLocationMutable.update {
                        if (data.isEmpty()) {
                            LocationsUIModel.Empty
                        } else {
                            LocationsUIModel.Data(data)
                        }
                    }
                }
        }
        viewModelScope.launch {
            locationRepository.recentLocationFlow
                .collect { data ->
                    recentLocationMutable.update {
                        if (data.isEmpty()) {
                            LocationsUIModel.Empty
                        } else {
                            LocationsUIModel.Data(data)
                        }
                    }
                }
        }
        getFavoriteLocations()
        getRecentLocations()
        getLocations()
    }

    fun search(input: String) {
        getLocations(input)
    }

    private fun getLocations(input: String = "") = viewModelScope.launch {
        currentJob?.cancel()
        locationMutable.value = LocationsUIModel.Loading
        currentJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                locationRepository.getLocations(input)
            }
        }
    }

    private fun getFavoriteLocations() = viewModelScope.launch {
        locationRepository.getFavoriteLocations()
    }

    private fun getRecentLocations() = viewModelScope.launch {
        locationRepository.getRecentLocations()
    }

    fun toggleFavorite(location: Location) {
        viewModelScope.launch {
            locationRepository.toggleFavorite(location)
            locationMutable.update { currentState ->
                if (currentState is LocationsUIModel.Data) {
                    val updatedLocations = currentState.locations.map {
                        if (it.name == location.name) {
                            it.copy(isFavorite = location.isFavorite)
                        } else {
                            it
                        }
                    }
                    LocationsUIModel.Data(updatedLocations)
                } else {
                    currentState
                }
            }
            getFavoriteLocations()
        }
    }

}

sealed class LocationsUIModel {
    object Empty : LocationsUIModel()
    object Loading : LocationsUIModel()
    data class Data(val locations: List<Location>) : LocationsUIModel()
}