package dk.dtu.weatherapp.ui.homepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Day(date: String, dayTemp: Int, nightTemp: Int, rain: Double, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {
            Icon(
                Icons.Default.ThumbUp, // @TODO use dynamic icon and desc
                contentDescription = "Dynamic weather icon",
                Modifier
                    .padding(end = 16.dp)
                    .size(36.dp)
            )
            Column {
                Text(date)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "$dayTemp / $nightTemp \u2103",Modifier.padding(end = 32.dp)) // @TODO use settings for unit
                    Text(text = "$rain mm") // @TODO use settings for unit
                }
            }
        }
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Arrow right",
            modifier = Modifier.size(36.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DayPrev() {
    Day("Wednesday 16. January", 21, 14, 2.4)
}