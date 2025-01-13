package dk.dtu.weatherapp.ui.statistics

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.GlobalUnits
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.navigation.Stats

@Composable
fun DayStats() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        Row{
            Text("Someway to choose a day")
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Information about what to see on this page",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        LazyVerticalGrid (
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                StatsCard(title = "Temperature", value = 20.0, R.drawable.i01d, GlobalUnits.temp) // TODO use imported values
            }
            item {
                StatsCard(title = "Wind", value = 4.0, R.drawable.wind, GlobalUnits.wind) // TODO use imported values
            }
            item {
                StatsCard(title = "Precipitation", value = 0.0, R.drawable.i09d, GlobalUnits.precipitation) // TODO use imported values
            }
            item {
                StatsCard(title = "Humidity", value = 80.51, R.drawable.humidity, "%") // TODO use imported values
            }
            item {
                StatsCard(title = "Pressure", value = 1027.0, R.drawable.compress, "hPa") // TODO use imported values
            }
            item {
                StatsCard(title = "Clouds", value = 90.2, R.drawable.i03d, "%") // TODO use imported values
            }
        }
    }
}

@Composable
fun StatsCard(title: String, value: Double, icon: Int, unit: String) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(
                painter = painterResource(icon),
                contentDescription = "null",
                modifier = Modifier
                    .size(24.dp)
            )
            Text(title, modifier = Modifier.padding(start = 4.dp))
        }
        Text(
            text = "$value $unit",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DayStatsPreview() {
    DayStats()
}