package dk.dtu.weatherapp.ui.homepage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.models.Hour

@Composable
fun HourlyForecast(forecast: List<Hour>, modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier.height(170.dp)) {
        items(forecast.size) { index ->
            val hour = forecast[index]
            Hour(
                time = hour.time,
                temp = hour.temp,
                rain = hour.rain,
                wind = hour.wind,
                windDegree = hour.windDegree,
                modifier
                    .padding(end = 20.dp)
                    .fillMaxHeight()
            )
            Spacer(modifier = modifier
                .width(2.dp)
                .fillMaxHeight()
                .background(Color.Gray)
            )
            Spacer(modifier = Modifier.padding(end = 20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HourlyPrev() {
    HourlyForecast(
        listOf(
            Hour("00:00", "01d", 15, 0.0, 5, 0),
            Hour("01:00", "01n", 14, 0.1, 6, 15),
            Hour("02:00", "02d", 13, 0.2, 7, 30),
            Hour("03:00", "02n", 12, 0.3, 8, 45),
            Hour("04:00", "03d", 11, 0.4, 9, 60),
            Hour("05:00", "03n", 10, 0.5, 10, 75)
        )
    )
}