package dk.dtu.weatherapp.ui.homepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Hour(time: String, temp: Int,rain: Double, wind: Int, windDegree: Int, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {
        Text(text = time)
        Icon(
            Icons.Default.ThumbUp,
            contentDescription = null, // @TODO use dynamic icon and description
            modifier = Modifier.size(50.dp)
        )
        Text(text = "$temp \u2103") // @TODO use either C \u2103 or F \u2109 depending on settings
        Text(text = "$rain mm")// @TODO use unit from settings
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Wind degree $windDegree",
                Modifier
                    .rotate(windDegree.toFloat()-90)
                    .size(16.dp)
            )
            Text(text = "$wind m/s") // @TODO use unit from settings
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HourPrev() {
    Hour("14:00",18,2.4,12, 93)
}