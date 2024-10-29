package dk.dtu.weatherapp.ui.homepage

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.R

data class Hour(
    val time: String,
    val iconURL: String,
    val temp: Int,
    val rain: Double,
    val wind: Int,
    val windDegree: Int
)

@Composable
fun HourlyForecast(forecast: Array<Hour>, modifier: Modifier = Modifier) {
    LazyRow(modifier = Modifier.height(170.dp)) {
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
    HourlyForecast(forecast)
}