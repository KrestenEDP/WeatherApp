package dk.dtu.weatherapp.ui.homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.ui.theme.Typography

@Composable
fun CurrentWeather(temp: Int, chill: Int, wind: Int, rain: Double) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "Dynamic weather icon", // @TODO use description from API
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text("$temp \u2103", style = Typography.titleLarge) // @TODO use either C \u2103 or F \u2109 depending on settings
            Text("Feels like $chill \u2103", fontSize = 14.sp, color = Color(0xFF5E5959)) // @TODO change font'
            Spacer(modifier = Modifier.height(6.dp))
            Row {
                Icon(
                    Icons.Default.ThumbUp, // @TODO use dynamic icon
                    contentDescription = "Current wind strength $wind meters per second",
                    modifier = Modifier.size(24.dp)
                )
                Text("$wind m/s", style = Typography.titleMedium) // @TODO use unit from settings
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row {
                Icon(
                    Icons.Default.ThumbUp, // @TODO use dynamic icon
                    contentDescription = "Current rain fall $rain millimeters",
                    modifier = Modifier.size(24.dp)
                )
                Text("$rain m/s", style = Typography.titleMedium) // @TODO use unit from settings
            }
        }
    }
}

@Preview
@Composable
fun CurrentPrev() {
    CurrentWeather(temp = 17, chill = 14, wind = 12, rain = 2.4)
}