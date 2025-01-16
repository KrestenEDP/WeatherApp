package dk.dtu.weatherapp.ui.homepage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dk.dtu.weatherapp.domain.fetchCurrentLocation
import dk.dtu.weatherapp.domain.getCurrentLocation
import dk.dtu.weatherapp.models.Location
import dk.dtu.weatherapp.ui.components.EmptyScreen
import dk.dtu.weatherapp.ui.components.LoadingScreen
import dk.dtu.weatherapp.ui.components.RequestErrorScreen


@Preview(showBackground = true)
@Composable
fun Preview() {
    Homepage(
        onDayClicked = {},
        onSearchClicked = {}
    )
}

@Composable
fun Homepage(
    onDayClicked: (Int) -> Unit,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val location = remember { mutableStateOf<Location?>(null) }
    val homepageViewModel: HomepageViewModel = viewModel()
    LaunchedEffect(Unit) {
        fetchCurrentLocation()
        homepageViewModel.setup()
        location.value = getCurrentLocation()
    }
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp, top=0.dp)
    ) {
        if (location.value != null) {
            Location(
                onSearchClicked = onSearchClicked,
                location = location.value!!.name
            )
        }
        when (val weatherUIModel = homepageViewModel.weatherUIState.collectAsState().value) {
            WeatherUIModel.RequestError -> RequestErrorScreen()
            WeatherUIModel.Empty -> EmptyScreen("No data")
            WeatherUIModel.Loading -> LoadingScreen()
            is WeatherUIModel.Data ->{
                Spacer(Modifier.height(20.dp))
                CurrentWeather(weatherUIModel.currentWeather)
                Spacer(modifier = Modifier.height(40.dp))
                HourlyForecast(weatherUIModel.hourly)
                Spacer(modifier = Modifier.height(20.dp))
                DailyForecast(onDayClicked = onDayClicked, weatherUIModel.daily, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}