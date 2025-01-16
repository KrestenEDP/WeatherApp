package dk.dtu.weatherapp.ui.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.GlobalUnits
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.models.StatsDay
import dk.dtu.weatherapp.ui.components.CircularList
import dk.dtu.weatherapp.ui.components.EmptyScreen
import dk.dtu.weatherapp.ui.components.LoadingScreen
import dk.dtu.weatherapp.ui.components.RequestErrorScreen

@Composable
fun DayStats(statsViewModel: StatsViewModel = remember { StatsViewModel() }) {
    var day by remember { mutableIntStateOf(1) }
    var month by remember { mutableIntStateOf(1) }

    LaunchedEffect(day, month) {
        statsViewModel.getDayStats(day, month)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DayPicker(day, month, onDayChange = { day = it }, onMonthChange = { month = it })
        }

        when (val statsDayUIModel = statsViewModel.dayUIState.collectAsState().value) {
            StatsDayUIModel.RequestError -> RequestErrorScreen()
            StatsDayUIModel.Empty -> EmptyScreen("No available data for this day")
            StatsDayUIModel.Loading -> LoadingScreen()
            is StatsDayUIModel.Data -> {
                StatsDayInformation(statsDayUIModel.day)
            }
        }
    }
}

@Composable
fun StatsDayInformation(
    statsDay: StatsDay?
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        statsDay?.let {
            item { StatsCard(title = "Temperature", value = it.temp.mean, R.drawable.i01d, GlobalUnits.temp) }
            item { StatsCard(title = "Wind", value = it.wind.mean, R.drawable.wind, GlobalUnits.wind) }
            item { StatsCard(title = "Rain", value = it.precipitation.mean, R.drawable.i09d, GlobalUnits.precipitation) }
            item { StatsCard(title = "Humidity", value = it.humidity.mean, R.drawable.humidity, "%") }
            item { StatsCard(title = "Pressure", value = it.pressure.mean, R.drawable.compress, "hPa") }
            item { StatsCard(title = "Clouds", value = it.clouds.mean, R.drawable.i03d, "%") }
        }
    }
}

@Composable
fun DayPicker(day: Int, month: Int, onDayChange: (Int) -> Unit, onMonthChange: (Int) -> Unit) {
    var listOfDays: MutableList<String> by remember { mutableStateOf(MutableList(1) { "" }) }
    val listOfMonths = listOf(
        "January", "February", "March", "April", "May", "June", "July", "August", "September",
        "October", "November", "December"
    ).toMutableList()
    listOfDays = when (month) {
        4, 6, 9, 11 -> listOf(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"
        ).toMutableList()
        2 -> listOf(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28"
        ).toMutableList()
        else -> listOf(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
        ).toMutableList()
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        CircularList(
            items = listOfDays,
            itemHeight = 40,
            itemDisplayCount = 3,
            width = 60,
        ) { onDayChange(it.toInt()) }
        CircularList(
            items = listOfMonths,
            itemHeight = 40,
            itemDisplayCount = 3,
            width = 150,
        ) { onMonthChange(
            when (it) {
                "January" -> 1
                "February" -> 2
                "March" -> 3
                "April" -> 4
                "May" -> 5
                "June" -> 6
                "July" -> 7
                "August" -> 8
                "September" -> 9
                "October" -> 10
                "November" -> 11
                "December" -> 12
                else -> 1
            }
        ) }
    }
}

@Preview(showBackground = true)
@Composable
fun DayStatsPreview() {
    DayStats()
}