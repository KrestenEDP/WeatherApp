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


val forecast = arrayOf(
    Hour("14:00", "someurl", 18, 2.4, 12, 0),
    Hour("14:00", "someurl", 18, 2.4, 12, 360),
    Hour("14:00", "someurl", 18, 2.4, 12, 90),
    Hour("14:00", "someurl", 18, 2.4, 12, 93),
    Hour("14:00", "someurl", 18, 2.4, 12, 93),
    Hour("14:00", "someurl", 18, 2.4, 12, 93),
    Hour("14:00", "someurl", 18, 2.4, 12, 93),
    Hour("14:00", "someurl", 18, 2.4, 12, 93),
    Hour("14:00", "someurl", 18, 2.4, 12, 93),
    Hour("14:00", "someurl", 18, 2.4, 12, 93),
)

val dailyForecast = arrayOf(
    Day("someurl", "Wednesday 16. January", 21, 14, 2.4),
    Day("someurl", "Wednesday 16. January", 21, 14, 2.4),
    Day("someurl", "Wednesday 16. January", 21, 14, 2.4),
    Day("someurl", "Wednesday 16. January", 21, 14, 2.4),
    Day("someurl", "Wednesday 16. January", 21, 14, 2.4),
    Day("someurl", "Wednesday 16. January", 21, 14, 2.4),
    Day("someurl", "Wednesday 16. January", 21, 14, 2.4),
    Day("someurl", "Wednesday 16. January", 21, 14, 2.4),
    Day("someurl", "Wednesday 16. January", 21, 14, 2.4),
    Day("someurl", "Wednesday 16. January", 21, 14, 2.4),
)

@Preview(showBackground = true)
@Composable
fun Preview() {
    Homepage(
        onDayClicked = {},
        "Dalby", 17, 14, 12, 2.4)
}

@Composable
fun Homepage(
    onDayClicked: (Int) -> Unit,
    location: String, temp: Int, chill: Int, wind: Int, rain: Double, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Location(location = location)
        Spacer(Modifier.height(50.dp))
        CurrentWeather(temp, chill, wind, rain)
        Spacer(modifier = Modifier.height(40.dp))
        HourlyForecast(forecast)
        Spacer(modifier = Modifier.height(20.dp))
        DailyForecast(
            onDayClicked = onDayClicked,
            dailyForecast, modifier = Modifier.fillMaxWidth())
    }
}
