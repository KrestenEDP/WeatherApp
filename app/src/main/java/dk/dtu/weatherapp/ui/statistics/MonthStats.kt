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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.GlobalUnits
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.models.StatsDay
import dk.dtu.weatherapp.ui.components.CircularList
import dk.dtu.weatherapp.ui.components.EmptyScreen
import dk.dtu.weatherapp.ui.components.LoadingScreen

@Composable
fun MonthStats(statsViewModel: StatsViewModel = remember { StatsViewModel() }) {
    var month by remember { mutableIntStateOf(1) }

    LaunchedEffect(month) {
        statsViewModel.getMonthStats(month)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        MonthPicker(onMonthChange = { month = it })

        when (val statsMonthUIModel = statsViewModel.monthUIState.collectAsState().value) {
            StatsMonthUIModel.Empty -> EmptyScreen("No available data for this month")
            StatsMonthUIModel.Loading -> LoadingScreen()
            is StatsMonthUIModel.Data -> {
                StatsMonthInformation(statsMonthUIModel.month)
            }
        }
    }
}

@Composable
fun StatsMonthInformation(
    statsMonth: StatsDay?
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        statsMonth?.let {
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
fun MonthPicker(onMonthChange: (Int) -> Unit) {
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    ).toMutableList()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularList(
            items = months,
            itemDisplayCount = 3,
            width = 140,
            onItemSelected = {
                onMonthChange(
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
                )
            }
        )
    }
}
