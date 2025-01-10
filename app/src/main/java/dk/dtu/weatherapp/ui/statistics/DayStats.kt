package dk.dtu.weatherapp.ui.statistics

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dk.dtu.weatherapp.ui.components.LoadingScreen

@Composable
fun DayStats() {
    val viewModel = StatsViewModel()
    var stats = viewModel.getStats()

    when (stats.isEmpty()) {
        true -> LoadingScreen()
        false -> Text("Temperature: ${stats[0].temp}")
    }
}