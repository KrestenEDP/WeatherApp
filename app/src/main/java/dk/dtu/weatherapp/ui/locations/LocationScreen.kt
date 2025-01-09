package dk.dtu.weatherapp.ui.locations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dk.dtu.weatherapp.Firebase.GetMacAddress
import dk.dtu.weatherapp.Firebase.LocationsViewModelFactory
import dk.dtu.weatherapp.ui.components.LoadingScreen



@Composable
fun LocationPage() {
    // Get the MAC address from the context
    val userId = GetMacAddress(LocalContext.current) ?: return

    // Now pass the `userId` to the ViewModel
    val locationsViewModel: LocationsViewModel = viewModel(
        factory = LocationsViewModelFactory(userId))

    Column(Modifier.fillMaxHeight()) {
        SearchField(locationsViewModel)
        when (val locationsUIModel = locationsViewModel.locationsUIState.collectAsState().value) {
            LocationsUIModel.Empty -> Text("No data")
            LocationsUIModel.Loading -> LoadingScreen()
            is LocationsUIModel.Data -> {
                LocationList(locations = locationsUIModel.locations, type = AllLocations)
            }
        }
        /*when (val favoriteLocationsUIModel = locationsViewModel.favoriteLocationsUIState.collectAsState().value) {
            LocationsUIModel.Empty -> Text("No data")
            LocationsUIModel.Loading -> LoadingScreen()
            is LocationsUIModel.Data -> {
                LocationList(locations = favoriteLocationsUIModel.locations, type = FavouriteLocations)
            }
        }*/
    }
}


@Preview(showBackground = true)
@Composable
fun LocationPagePrev() {
    LocationPage()
}