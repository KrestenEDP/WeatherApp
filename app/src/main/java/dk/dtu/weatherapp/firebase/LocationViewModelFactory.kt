package dk.dtu.weatherapp.firebase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dk.dtu.weatherapp.ui.locations.LocationsViewModel

class LocationsViewModelFactory(private val userId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationsViewModel::class.java)) {
            return LocationsViewModel(userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
