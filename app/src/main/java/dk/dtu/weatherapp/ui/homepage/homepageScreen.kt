package dk.dtu.weatherapp.ui.homepage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.data.mock.MockCurrentWeatherDataSource
import dk.dtu.weatherapp.data.mock.MockDayDataSource
import dk.dtu.weatherapp.data.mock.MockHourDataSource


@Preview(showBackground = true)
@Composable
fun Preview() {
    Homepage()
}

@Composable
fun Homepage(modifier: Modifier = Modifier) {
    val current = MockCurrentWeatherDataSource().getCurrentWeather()
    val days = MockDayDataSource().getDailyWeather()
    val hours = MockHourDataSource().getHourlyWeather()
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Location(location = "Dalby") // @TODO Don't hardcode location
        Spacer(Modifier.height(50.dp))
        CurrentWeather(current)
        Spacer(modifier = Modifier.height(40.dp))
        HourlyForecast(hours)
        Spacer(modifier = Modifier.height(20.dp))
        DailyForecast(days, modifier = Modifier.fillMaxWidth())
    }
}