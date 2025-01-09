package dk.dtu.weatherapp.ui.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.dtu.weatherapp.domain.LocationRepository
import dk.dtu.weatherapp.models.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationsViewModel(userId: String) : ViewModel() {
    private val locationRepository = LocationRepository(userId)

    private val locationMutable = MutableStateFlow<LocationsUIModel>(LocationsUIModel.Loading)
    val locationsUIState: StateFlow<LocationsUIModel> = locationMutable

    private val favoriteLocationMutable = MutableStateFlow<LocationsUIModel>(LocationsUIModel.Loading)
    val favoriteLocationsUIState: StateFlow<LocationsUIModel> = favoriteLocationMutable

    private val _locationState = MutableLiveData<List<Location>>()
    val locationState: LiveData<List<Location>> = _locationState


    init {
        viewModelScope.launch {
            locationRepository.locationFlow.collect { data ->
                locationMutable.update { LocationsUIModel.Data(data) }
            }
        }

        viewModelScope.launch {
            locationRepository.favoriteLocationFlow.collect { data ->
                favoriteLocationMutable.update { LocationsUIModel.Data(data) }
            }
        }
        getLocations()
        getFavoriteLocations()
    }

    fun search(input: String) {
        getLocations(input)
    }

    private fun getLocations(input: String = "Lyng") = viewModelScope.launch {
        locationRepository.getLocations(input)
    }

    private fun getFavoriteLocations() = viewModelScope.launch {
        locationRepository.getFavoriteLocations()
    }

    fun updateFavorites() {
        viewModelScope.launch {
            val favoriteLocations = locationRepository.fetchFavorites()
            _locationState.value = favoriteLocations
        }
    }

    fun addFavorite(location: Location) {
        viewModelScope.launch {
            locationRepository.addFavorite(location)
            updateFavorites() // Refresh favorites list
        }
    }

    fun removeFavorite(location: Location) {
        viewModelScope.launch {
            locationRepository.removeFavorite(location)
            updateFavorites() // Refresh favorites list
        }
    }
}

sealed class LocationsUIModel {
    object Empty : LocationsUIModel()
    object Loading : LocationsUIModel()
    data class Data(val locations: List<Location>) : LocationsUIModel()
}
