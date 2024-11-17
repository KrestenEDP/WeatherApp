package dk.dtu.weatherapp.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val temperatureNames= listOf(
    "Celsius (°C)", "Fahrenheit (°F)", "Kelvin (°K)"
)

private val temperatureUnits = listOf(
    "°C", "°F", "°K"
)

private val windUnits = listOf(
    "m/s", "km/h"
)

private const val rowHeight = 60

@Composable
fun WeatherSettingsPage(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxWidth()
    ) {
        DarkMode(modifier = Modifier.height(rowHeight.dp).fillMaxWidth())
        DynamicBackground(modifier = Modifier.height(rowHeight.dp).fillMaxWidth())
        TemperatureUnit(modifier = Modifier.height(rowHeight.dp).fillMaxWidth())
        WindSpeedUnit(modifier = Modifier.height(rowHeight.dp).fillMaxWidth())
        PushNotification(modifier = Modifier.height(rowHeight.dp).fillMaxWidth())
    }
}

@Composable
private fun DarkMode(modifier: Modifier = Modifier) {
    // If this is set to true, darkMode is on, aka "enabled",
    // aka "turned on", aka "horny"
    var darkModeOn by remember { mutableStateOf(true) }
    Row (
        modifier = modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text (
            text = "Dark mode",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,

        )


        Switch(
            checked = darkModeOn,
            onCheckedChange = {
                darkModeOn = it
            },
            enabled = true
        )
    }
}

@Composable
private fun DynamicBackground(modifier: Modifier = Modifier) {
    // If this is set to true, dynamic background is on, aka "enabled",
    // aka "turned on", aka "horny"
    var dynamicBackgroundOn by remember { mutableStateOf(true) }
    Row (
        modifier = modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text (
            text = "Dynamic background",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )


        Switch(
            modifier = Modifier.padding(0.dp),
            checked = dynamicBackgroundOn,
            onCheckedChange = {
                dynamicBackgroundOn = it
            },
            enabled = true
        )
    }
}

@Composable
private fun TemperatureUnit(modifier: Modifier = Modifier) {
    // If unit 0 it is celsius, if 1 it is fahrenheit and if 2 it is kelvin
    var temperatureUnit by remember { mutableIntStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    val unit = tempToString(temperatureUnit)
    Row (
        modifier = modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Temperature unit",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )

        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = unit,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Drop down menu")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(temperatureNames[0]) },
                        onClick = {
                            temperatureUnit = 0
                            expanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text(temperatureNames[1]) },
                        onClick = {
                            temperatureUnit = 1
                            expanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text(temperatureNames[2]) },
                        onClick = {
                            temperatureUnit = 2
                            expanded = false }
                    )
                }
            }

        }
    }
}

@Composable
private fun WindSpeedUnit(modifier: Modifier = Modifier) {
    // If unit 0 it is m/s and if 1 it is km/h
    var windUnit by remember { mutableIntStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    val unit = windToString(windUnit)
    Row (
        modifier = modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Wind speed unit",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )

        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = unit,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Drop down menu")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("m/s") },
                        onClick = {
                            windUnit = 0
                            expanded = false },
                    )
                    DropdownMenuItem(
                        text = { Text("km/h") },
                        onClick = {
                            windUnit = 1
                            expanded = false },
                    )
                }
            }

        }
    }
}

@Composable
private fun PushNotification(modifier: Modifier = Modifier) {
    // If this is set to true, darkMode is on, aka "enabled",
    // aka "turned on", aka "horny"
    var pushNotificationsOn by remember { mutableStateOf(true) }
    Row (
        modifier =  modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text (
            text = "Push notifications",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )

        Switch(
            checked = pushNotificationsOn,
            onCheckedChange = {
                pushNotificationsOn = it
            },
            enabled = true
        )
    }
}

@Preview (showBackground = true)
@Composable
fun PagePreview() {
    WeatherSettingsPage()
}

fun tempToString(temp: Int): String {
    var unit = ""
    unit = when (temp) {
        0 -> temperatureUnits[0]
        1 -> temperatureUnits[1]
        else -> temperatureUnits[2]
    }

    return unit
}

fun windToString(wind: Int): String {
    var unit = ""
    if (wind == 0) {
        unit = windUnits[0]
    } else if (wind == 1) {
        unit = windUnits[1]
    }

    return unit
}