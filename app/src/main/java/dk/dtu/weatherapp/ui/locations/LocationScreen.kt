package dk.dtu.weatherapp.ui.locations

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dk.dtu.weatherapp.Firebase.GetMacAddress
import dk.dtu.weatherapp.Firebase.LocationsViewModelFactory


@Composable
fun LocationPage() {
    // Get the MAC address from the context
    val userId = GetMacAddress(LocalContext.current) ?: return

    // Now pass the `userId` to the ViewModel
    val locationsViewModel: LocationsViewModel = viewModel(
        factory = LocationsViewModelFactory(userId))

    var showFavoriteRecent = remember { mutableStateOf(true) }
    SearchField(onValueChange = {
        locationsViewModel.search(it)
        showFavoriteRecent.value = it.isEmpty()
    })
    LazyColumn(Modifier.fillMaxHeight()) {
        item {
            if (showFavoriteRecent.value) {
                LocationList(
                    locationsUIModel = locationsViewModel.favoriteLocationsUIState.collectAsState().value,
                    type = FavoriteLocations,
                    onToggleFavorite = { location ->
                        locationsViewModel.toggleFavorite(location)
                    }
                )
                LocationList(
                    locationsUIModel = locationsViewModel.recentLocationsUIState.collectAsState().value,
                    type = RecentLocations,
                    onToggleFavorite = { location ->
                        locationsViewModel.toggleFavorite(location)
                    }
                )
            } else {
                LocationList(
                    locationsUIModel = locationsViewModel.locationsUIState.collectAsState().value,
                    type = SearchLocations,
                    onToggleFavorite = { location ->
                        locationsViewModel.toggleFavorite(location)
                    }
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LocationPagePrev() {
    LocationPage()
}