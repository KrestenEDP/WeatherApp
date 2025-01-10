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
<<<<<<< Updated upstream
import kotlinx.coroutines.withContext
=======
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
>>>>>>> Stashed changes

class LocationsViewModel(userId: String) : ViewModel() {
    private val locationRepository = LocationRepository(userId)

    private val locationMutable = MutableStateFlow<LocationsUIModel>(LocationsUIModel.Loading)
    val locationsUIState: StateFlow<LocationsUIModel> = locationMutable

    private val favoriteLocationMutable = MutableStateFlow<LocationsUIModel>(LocationsUIModel.Loading)
    val favoriteLocationsUIState: StateFlow<LocationsUIModel> = favoriteLocationMutable

<<<<<<< Updated upstream
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
=======
    private val _locationState = MutableLiveData<List<Location>>()
    val locationState: LiveData<List<Location>> = _locationState

    init {
        viewModelScope.launch {
            locationRepository.locationFlow.collect { data ->
                locationMutable.update { LocationsUIModel.Data(data) }
            }
>>>>>>> Stashed changes
        }
        viewModelScope.launch {
            locationRepository.favoriteLocationFlow.collect { data ->
                favoriteLocationMutable.update { LocationsUIModel.Data(data) }
            }
        }
        getFavoriteLocations()
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