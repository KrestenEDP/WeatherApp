package dk.dtu.weatherapp.ui.homepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Day(
    val iconURL: String,
    val date: String,
    val dayTemp: Int,
    val nightTemp: Int,
    val rain: Double
)

@Composable
fun DailyForecast(dailycast: Array<Day>, modifier: Modifier = Modifier) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        items(dailycast.size) { index ->
            val day = dailycast[index]
            Day(
                day.date,
                day.dayTemp,
                day.nightTemp,
                day.rain,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}

@Preview
@Composable
fun DailyPrev() {
    DailyForecast(dailycast)
}