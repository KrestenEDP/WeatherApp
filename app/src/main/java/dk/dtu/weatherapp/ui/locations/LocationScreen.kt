package dk.dtu.weatherapp.ui.locations

import androidx.compose.foundation.layout.Column
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
import dk.dtu.weatherapp.models.Location


@Composable
fun LocationPage(onLocationClicked: (Location) -> Unit) {
    val userId = GetMacAddress(LocalContext.current) ?: return
    val locationsViewModel: LocationsViewModel = viewModel(factory = LocationsViewModelFactory(userId))
    
    var showFavoriteRecent = remember { mutableStateOf(true) }
    Column {
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
                        },
                        onLocationClicked = onLocationClicked
                    )
                    LocationList(
                        locationsUIModel = locationsViewModel.recentLocationsUIState.collectAsState().value,
                        type = RecentLocations,
                        onToggleFavorite = { location ->
                            locationsViewModel.toggleFavorite(location)
                        },
                        onLocationClicked = onLocationClicked
                    )
                } else {
                    LocationList(
                        locationsUIModel = locationsViewModel.locationsUIState.collectAsState().value,
                        type = SearchLocations,
                        onToggleFavorite = { location ->
                            locationsViewModel.toggleFavorite(location)
                        },
                        onLocationClicked = onLocationClicked
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LocationPagePrev() {
    LocationPage(onLocationClicked = {})
}