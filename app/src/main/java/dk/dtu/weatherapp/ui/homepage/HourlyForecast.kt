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
            Spacer(modifier = Modifier.padding(end = 20.dp))
            Hour(
                time = hour.time,
                temp = hour.temp,
                rain = hour.precipitation,
                wind = hour.wind,
                windDegree = hour.windDegree,
                icon = hour.iconURL,
                modifier
                    .padding(end = 20.dp)
                    .fillMaxHeight()
            )
            Spacer(modifier = modifier
                .width(2.dp)
                .fillMaxHeight()
                .background(Color.Gray)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HourlyPrev() {
    HourlyForecast(
        listOf(
            Hour("00:00", "i01n", 15.0, 0.0, 5.0, 0),
            Hour("01:00", "i01n", 14.0, 0.1, 6.0, 15),
            Hour("02:00", "i01n", 13.0, 0.2, 7.0, 30),
            Hour("03:00", "i01n", 12.0, 0.3, 8.0, 45),
            Hour("04:00", "i01n", 11.0, 0.4, 9.0, 60),
            Hour("05:00", "i01n", 10.0, 0.5, 10.0, 75)
        )
    )
}