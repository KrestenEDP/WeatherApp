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
import dk.dtu.weatherapp.data.mock.MockCurrentWeatherDataSource
import dk.dtu.weatherapp.data.mock.MockDayDataSource
import dk.dtu.weatherapp.data.mock.MockHourDataSource


@Preview(showBackground = true)
@Composable
fun Preview() {
    Homepage(
        onDayClicked = {}
    )
}

@Composable
fun Homepage(onDayClicked: (Int) -> Unit, modifier: Modifier = Modifier) {
    val homepageViewModel: HomepageViewModel = viewModel()
    val currentWeatherUIModel = homepageViewModel.weatherUIState.collectAsState().value
    val dailyForecastUIModel = homepageViewModel.dailyUIState.collectAsState().value
    val hourlyForecastUIModel = homepageViewModel.hourlyUIState.collectAsState().value

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Location(location = "Dalby") // @TODO Don't hardcode location
        Spacer(Modifier.height(50.dp))
        when (currentWeatherUIModel) {
            WeatherUIModel.Empty -> Text("No data")
            WeatherUIModel.Loading -> Text("Loading")
            is WeatherUIModel.Data -> CurrentWeather(currentWeatherUIModel.currentWeather)
        }
        Spacer(modifier = Modifier.height(40.dp))
        when (hourlyForecastUIModel) {
            HourlyUIModel.Empty -> Text("No data")
            HourlyUIModel.Loading -> Text("Loading")
            is HourlyUIModel.Data -> HourlyForecast(hourlyForecastUIModel.hourly)
        }
        Spacer(modifier = Modifier.height(20.dp))
        when (dailyForecastUIModel) {
            DailyUIModel.Empty -> Text("No data")
            DailyUIModel.Loading -> Text("Loading")
            is DailyUIModel.Data ->
                DailyForecast(onDayClicked = onDayClicked, dailyForecastUIModel.daily, modifier = Modifier.fillMaxWidth())
        }
    }
}