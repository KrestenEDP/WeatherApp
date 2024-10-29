package dk.dtu.weatherapp.ui.homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.ui.theme.Typography


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

val dailycast = arrayOf(
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
    Homepage("Dalby", 17, 14, 12, 2.4)
}
@Composable
fun Homepage(location: String, temp: Int, chill: Int, wind: Int, rain: Double) {
    Column(modifier = Modifier.fillMaxSize()) {
        Location(location = location)
        Spacer(Modifier.height(50.dp))
        CurrentWeather(temp, chill, wind, rain)
        Spacer(modifier = Modifier.height(40.dp))
        HourlyForecast(forecast)
        Spacer(modifier = Modifier.height(20.dp))
        DailyForecast(dailycast, modifier = Modifier.fillMaxWidth())
    }
}
