package dk.dtu.weatherapp.ui.locations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LocationPage(){
    val locationsViewModel: LocationsViewModel = viewModel()
    when (val locationsUIModel = locationsViewModel.locationsUIState.collectAsState().value) {
        LocationsUIModel.Empty -> Text("No data")
        LocationsUIModel.Loading -> Text("Loading")
        is LocationsUIModel.Data -> {
            Column(Modifier.fillMaxHeight()){
                SearchField()
                LocationList(locationsUIModel.locations, AllLocations)
                LocationList(locationsUIModel.locations, FavouriteLocations)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationPagePrev() {
    LocationPage()
}