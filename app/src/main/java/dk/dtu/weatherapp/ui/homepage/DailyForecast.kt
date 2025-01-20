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
        modifier = modifier
    ) {
        days.forEachIndexed { index, day ->
            Day(
                day.date,
                day.dayTemp,
                day.nightTemp,
                day.precipitation,
                day.iconURL,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 8.dp)
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
            Day("i01n", "Wednesday 16. January", 21.0, 14.0, 2.4, 5.0, 7.0, 180, 90, 1000, 80, "07:00", "16:00"),
            Day("i01n", "Thursday 17. January", 19.0, 13.0, 2.1, 4.0, 6.0, 190, 80, 1005, 85, "07:01", "16:01"),
            Day("i01n", "Friday 18. January", 17.0, 12.0, 2.0, 3.0, 5.0, 200, 70, 1010, 90, "07:02", "16:02"),
            Day("i01n", "Saturday 19. January", 24.0, 15.0, 5.2, 6.0, 8.0, 210, 60, 1015, 95, "07:03", "16:03"),
            Day("i01n", "Sunday 20. January", 14.0, 11.0, 0.0, 2.0, 4.0, 220, 50, 1020, 100, "07:04", "16:04"),
            Day("i01n", "Monday 21. January", 25.0, 12.0, 0.0, 1.0, 3.0, 230, 40, 1025, 105, "07:05", "16:05"),
            Day("i01n", "Tuesday 22. January", 12.0, 13.0, 1.1, 0.0, 2.0, 240, 30, 1030, 110, "07:06", "16:06"),
            Day("i01n", "Wednesday 23. January", 11.0, 13.0, 0.0, 0.0, 1.0, 250, 20, 1035, 115, "07:07", "16:07"),
        )
    )
}