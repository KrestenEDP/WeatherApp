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
    private var currentJob: Job? = null
    private val locationRepository = LocationRepository(userId)

    private val locationMutable = MutableStateFlow<LocationsUIModel>(LocationsUIModel.Loading)
    val locationsUIState: StateFlow<LocationsUIModel> = locationMutable

    private val favoriteLocationMutable = MutableStateFlow<LocationsUIModel>(LocationsUIModel.Loading)
    val favoriteLocationsUIState: StateFlow<LocationsUIModel> = favoriteLocationMutable

    init {
        viewModelScope.launch {
            start()
        }
        //getFavoriteLocations()
    }

    suspend fun start() {
        withContext(Dispatchers.IO) {
            viewModelScope.launch {
                locationRepository.locationFlow
                    .collect { data ->
                        if (data.isEmpty()) {
                            locationMutable.update {
                                LocationsUIModel.Empty
                            }
                        } else {
                            locationMutable.update {
                                LocationsUIModel.Data(data)
                            }
                        }
                    }

            }
            viewModelScope.launch {
                locationRepository.favoriteLocationFlow
                    .collect { data ->
                        favoriteLocationMutable.update {
                            LocationsUIModel.Data(data)
                        }
                    }
            }

            viewModelScope.launch {
                locationRepository.preprocessCities()
                getLocations("")
            }
        }
    }

    fun search(input: String = "") {
        getLocations(input)
    }

    private fun getLocations(input: String) {
        currentJob?.cancel()
        locationMutable.value = LocationsUIModel.Loading

        currentJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                locationRepository.getLocations(input)
            }
        }
    }
    private fun getFavoriteLocations() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            locationRepository.getFavoriteLocations()
        }
    }
}

sealed class LocationsUIModel {
    object Empty : LocationsUIModel()
    object Loading : LocationsUIModel()
    data class Data(val locations: List<Location>) : LocationsUIModel()
}