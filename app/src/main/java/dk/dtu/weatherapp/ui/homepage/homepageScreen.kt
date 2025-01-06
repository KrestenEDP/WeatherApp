package dk.dtu.weatherapp.ui.homepage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dk.dtu.weatherapp.ui.components.LoadingScreen


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
    val homepageViewModel: HomepageViewModel = viewModel()

    when (val weatherUIModel = homepageViewModel.weatherUIState.collectAsState().value) {
        WeatherUIModel.Empty -> Text("No data")
        WeatherUIModel.Loading -> LoadingScreen()
        is WeatherUIModel.Data ->{
            HomePageContent(weatherUIModel, onDayClicked, onSearchClicked, modifier)
        }
    }
}

@Composable
fun HomePageContent(
    weatherUIModel: WeatherUIModel.Data,
    onDayClicked: (Int) -> Unit,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp, top=0.dp)
    ) {
        Location(
            onSearchClicked = onSearchClicked,
            location = "Dalby" // @TODO Don't hardcode location
        )
        Spacer(Modifier.height(20.dp))
        CurrentWeather(weatherUIModel.currentWeather)
        Spacer(modifier = Modifier.height(40.dp))
        HourlyForecast(weatherUIModel.hourly)
        Spacer(modifier = Modifier.height(20.dp))
        DailyForecast(onDayClicked = onDayClicked, weatherUIModel.daily, modifier = Modifier.fillMaxWidth())
    }
}