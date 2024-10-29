package dk.dtu.weatherapp.ui.alerts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Alertdata(
    val title: String,
    val text: String
)

val alerts = arrayOf(
    Alertdata("Thunderstorm",
        "Thunderstorm in Dalby 19:00 local time"),
    Alertdata("Landslide", "Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
            " sed do eiusmod  tempor incididunt ut labore et dolore magna aliqua. Ut enim ad" +
            " minim  veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea" +
            "  commodo consequat. Duis aute irure dolor in reprehenderit in voluptate  velit" +
            " esse cillum dolore eu fugiat nulla pariatur. Excepteur sint  occaecat cupidatat" +
            " non proident, sunt in culpa qui officia deserunt  mollit anim id est laborum.")
)

@Composable
fun AlertsScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(alerts.size) { index ->
            Alert(
                title = alerts[index].title,
                text = alerts[index].text
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AlertsScreenPrev() {
    AlertsScreen(modifier = Modifier.fillMaxSize())
}