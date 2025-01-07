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
import dk.dtu.weatherapp.models.Day

@Composable
fun DailyForecast(onDayClicked: (Int) -> Unit, days: List<Day>, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        days.forEachIndexed { index, day ->
            if (index < 5) {
                Day(
                    day.date,
                    day.dayTemp,
                    day.nightTemp,
                    day.rain,
                    day.iconURL,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .clickable { onDayClicked(index) }
                )
            } else {
                Day(
                    day.date,
                    day.dayTemp,
                    day.nightTemp,
                    day.rain,
                    day.iconURL,
                    clickable = false,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun DailyPrev() {
    DailyForecast(
        onDayClicked = { },
        listOf(
            Day("i01n", "Wednesday 16. January", 21.0, 14.0, 2.4),
            Day("i01n", "Thursday 17. January", 19.0, 13.0, 2.1),
            Day("i01n", "Friday 18. January", 17.0, 12.0, 2.0),
            Day("i01n", "Saturday 19. January", 24.0, 15.0, 5.2),
            Day("i01n", "Sunday 20. January", 14.0, 11.0, 0.0),
            Day("i01n", "Monday 21. January", 25.0, 12.0, 0.0),
            Day("i01n", "Tuesday 22. January", 12.0, 13.0, 1.1),
            Day("i01n", "Wednesday 23. January", 11.0, 13.0, 0.0)
        )
    )
}