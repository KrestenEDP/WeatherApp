package dk.dtu.weatherapp.ui.homepage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
fun DailyForecast(
    onDayClicked: (Int) -> Unit,
    dailyForecast: Array<Day>,
    modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        dailyForecast.forEachIndexed { index, day ->
            Day(
                day.date,
                day.dayTemp,
                day.nightTemp,
                day.rain,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clickable { onDayClicked(index) }
            )
        }
    }
}

@Preview
@Composable
fun DailyPrev() {
    DailyForecast(onDayClicked = { } ,dailyForecast)
}