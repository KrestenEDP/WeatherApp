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
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.models.Day

@Composable
fun DailyForecast(onDayClicked: (Int) -> Unit, days: List<Day>, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        days.forEachIndexed { index, day ->
            Day(
                day.date,
                day.dayTemp,
                day.nightTemp,
                day.rain,
                day.iconURL,
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
    DailyForecast(
        onDayClicked = { },
        listOf(
            Day(R.drawable.i01n, "Wednesday 16. January", 21, 14, 2.4),
            Day(R.drawable.i01n, "Thursday 17. January", 19, 13, 2.1),
            Day(R.drawable.i01n, "Friday 18. January", 17, 12, 2.0),
            Day(R.drawable.i01n, "Saturday 19. January", 24, 15, 5.2),
            Day(R.drawable.i01n, "Sunday 20. January", 14, 11, 0.0),
            Day(R.drawable.i01n, "Monday 21. January", 25, 12, 0.0),
            Day(R.drawable.i01n, "Tuesday 22. January", 12, 13, 1.1),
            Day(R.drawable.i01n, "Wednesday 23. January", 11, 13, 0.0)
        )
    )
}