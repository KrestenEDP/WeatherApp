package dk.dtu.weatherapp.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.models.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LocationsViewModel(private val userId: String) : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    private val _locationsUIState = MutableStateFlow<LocationsUIModel>(LocationsUIModel.Loading)
    val locationsUIState: StateFlow<LocationsUIModel> = _locationsUIState

    init {
        getFavoriteCities()
    }

    private fun getFavoriteCities() = viewModelScope.launch {
        _locationsUIState.value = LocationsUIModel.Loading

        try {
            val favoritesCollection = firestore.collection("users")
                .document(userId)
                .collection("favorites")

            favoritesCollection.get().addOnSuccessListener { result ->
                val favoriteCities = mutableListOf<Location>()
                for (document in result) {
                    val cityName = document.id
                    favoriteCities.add(Location(name = cityName, 15.5, R.drawable.i01n))
                }

                _locationsUIState.value = LocationsUIModel.Data(favoriteCities)
            }.addOnFailureListener {
                _locationsUIState.value = LocationsUIModel.Empty
            }
        } catch (e: Exception) {
            _locationsUIState.value = LocationsUIModel.Empty
        }
    }
}

sealed class LocationsUIModel {
    object Empty : LocationsUIModel()
    object Loading : LocationsUIModel()
    data class Data(val locations: List<Location>) : LocationsUIModel()
}