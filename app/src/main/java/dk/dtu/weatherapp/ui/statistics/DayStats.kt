package dk.dtu.weatherapp.ui.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.dtu.weatherapp.GlobalUnits
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.models.StatsDay
import dk.dtu.weatherapp.ui.components.CircularList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayStats(statsViewModel: StatsViewModel = remember { StatsViewModel() }) {
    val tooltipState = rememberTooltipState(isPersistent = true)
    var day by remember { mutableIntStateOf(1) }
    var month by remember { mutableIntStateOf(1) }
    var statsDay by remember { mutableStateOf<StatsDay?>(null) }

    LaunchedEffect(day, month) {
        statsDay = statsViewModel.getDayStats(day, month)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(if (tooltipState.isVisible) Modifier.blur(2.dp) else Modifier)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            DayPicker(day, month, onDayChange = { day = it }, onMonthChange = { month = it })
            TooltipBox(
                positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                tooltip = {
                    RichTooltip(
                        title = { Text(stringResource(R.string.statsTooltipTitle)) },
                        text = { Text(stringResource(R.string.statsTooltipText)) },
                        action = {
                            TextButton(
                                onClick = { tooltipState.dismiss() }
                            ) {
                                Text(stringResource(R.string.statsTooltipButton))
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                    )
                },
                state = tooltipState,
            ) {
                IconButton(
                    onClick = { CoroutineScope(Dispatchers.Main).launch { tooltipState.show() } }
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Information about what to see on this page",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
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
    Row {
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
            width = 140,
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

@Composable
fun StatsCard(title: String, value: Double, icon: Int, unit: String) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "null",
                    modifier = Modifier
                        .size(16.dp)
                )
                Text(
                    title,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Text(
                text = "$value $unit",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DayStatsPreview() {
    DayStats()
}