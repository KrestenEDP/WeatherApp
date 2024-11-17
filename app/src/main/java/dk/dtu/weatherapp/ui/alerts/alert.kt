package dk.dtu.weatherapp.ui.alerts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Alert(title: String, text: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(start = 16.dp, end = 16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(bottom = 4.dp).padding(end = 16.dp)
        ) {
            Icon(
                Icons.Default.ThumbUp, // @TODO use dynamic icon
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(24.dp)
            )
            Text(
                text = title,
                fontSize = 24.sp
            )
        }
        Text(text = "From 15. January to 19 January")
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun AlertPrev() {
    Alert(
        "Thunderstorm",
        "Thunderstorm in Dalby 19:00 local time"
    )
}