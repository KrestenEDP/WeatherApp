package dk.dtu.weatherapp.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.shape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.dtu.weatherapp.ui.theme.Typography

private val temperatureNames= listOf(
    "Celsius (°C)", "Fahrenheit (°F)", "Kelvin (°K)"
)

private val temperatureUnits = listOf(
    "°C", "°F", "°K"
)

private val rowHeight = 60

@Composable
fun WeatherSettingsPage(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxWidth()
    ) {
        darkMode(modifier = Modifier.height(rowHeight.dp).fillMaxWidth())
        dynamicBackground(modifier = Modifier.height(rowHeight.dp).fillMaxWidth())
        temperatureUnit(modifier = Modifier.height(rowHeight.dp).fillMaxWidth())
        pushNotification(modifier = Modifier.height(rowHeight.dp).fillMaxWidth())
    }
}

@Composable
private fun darkMode(modifier: Modifier = Modifier) {
    // If this is set to true, darkmode is on, aka "enabled",
    // aka "turned on", aka "horny"
    var darkmodeOn by remember { mutableStateOf(true) }
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
            checked = darkmodeOn,
            onCheckedChange = {
                darkmodeOn = it
            },
            enabled = true
        )
    }
}

@Composable
private fun dynamicBackground(modifier: Modifier = Modifier) {
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
private fun temperatureUnit(modifier: Modifier = Modifier) {
    // If unit 0 it is celcius, if 1 it is fahrenheit and if 2 it is kelvin
    var temperatureUnit by remember { mutableIntStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    var unit = tempToString(temperatureUnit)
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

        Box() {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$unit",
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
                        text = { Text("Celsius") },
                        onClick = {
                            temperatureUnit = 0
                            expanded = false },
                        leadingIcon = { Text("°C") }
                    )
                    DropdownMenuItem(
                        text = { Text("Fahrenheit") },
                        onClick = {
                            temperatureUnit = 1
                            expanded = false },
                        leadingIcon = { Text("°F") }
                    )
                    DropdownMenuItem(
                        text = { Text("Kelvin") },
                        onClick = {
                            temperatureUnit = 2
                            expanded = false },
                        leadingIcon = { Text("°K") }
                    )
                }
            }

        }
    }
}

@Composable
private fun pushNotification(modifier: Modifier = Modifier) {
    // If this is set to true, darkmode is on, aka "enabled",
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
    if (temp == 0) {
        unit = temperatureUnits[0]
    } else if (temp == 1) {
        unit = temperatureUnits[1]
    } else
        unit = temperatureUnits[2]

    return unit
}
