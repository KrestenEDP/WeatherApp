package dk.dtu.weatherapp.ui.settings

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import dk.dtu.weatherapp.domain.dataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val unitNames= listOf(
    "Celsius (°C) | meters/sec", "Fahrenheit (°F) | miles/hour", "Kelvin (°K) | meters/sec"
)

private val temperatureUnits = listOf(
    "Metric", "Imperial", "Scientific"
)

private const val rowHeight = 60

@Composable
fun WeatherSettingsPage(context: Context) {
    Column(modifier = Modifier.fillMaxWidth()) {
        DarkModeSetting(context)
        DynamicBackgroundSetting(context)
        UnitSetting(context)
        PushNotificationsSetting(context)
    }
}

@Composable
fun DarkModeSetting(context: Context) {
    val dataStore = context.dataStore
    val coroutineScope = rememberCoroutineScope()
    val darkModeKey = booleanPreferencesKey("dark_mode")

    var isDarkModeEnabled by remember { mutableStateOf(false) }

    // Read value from DataStore
    LaunchedEffect(dataStore) {
        dataStore.data.map { preferences ->
            preferences[darkModeKey] ?: true
        }.collect { darkModeValue ->
            isDarkModeEnabled = darkModeValue
        }
    }

    // UI
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Dark Mode", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Switch(
            checked = isDarkModeEnabled,
            onCheckedChange = { newValue ->
                isDarkModeEnabled = newValue
                coroutineScope.launch {
                    dataStore.edit { preferences ->
                        preferences[darkModeKey] = newValue
                    }
                }
            }
        )
    }
}


@Composable
fun DynamicBackgroundSetting(context: Context) {
    val dataStore = context.dataStore
    val coroutineScope = rememberCoroutineScope()
    val dynamicBackgroundKey = booleanPreferencesKey("dynamic_background")

    var isDynamicBackgroundEnabled by remember { mutableStateOf(false) }

    // Read value from DataStore
    LaunchedEffect(dataStore) {
        dataStore.data.map { preferences ->
            preferences[dynamicBackgroundKey] ?: false
        }.collect { value ->
            isDynamicBackgroundEnabled = value
        }
    }

    // UI
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Dynamic Background", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Switch(
            checked = isDynamicBackgroundEnabled,
            onCheckedChange = { newValue ->
                isDynamicBackgroundEnabled = newValue
                coroutineScope.launch {
                    dataStore.edit { preferences ->
                        preferences[dynamicBackgroundKey] = newValue
                    }
                }
            }
        )
    }
}


@Composable
fun UnitSetting(context: Context) {
    val dataStore = context.dataStore
    val coroutineScope = rememberCoroutineScope()
    val preferredUnitKey = intPreferencesKey("preferred_unit")

    var selectedUnit by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }

    // Read value from DataStore
    LaunchedEffect(dataStore) {
        dataStore.data.map { preferences ->
            preferences[preferredUnitKey] ?: 0
        }.collect { value ->
            selectedUnit = value
        }
    }

    // UI
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Unit", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Box {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = temperatureUnits[selectedUnit], fontSize = 18.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Dropdown Menu")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    unitNames.forEachIndexed { index, name ->
                        DropdownMenuItem(
                            text = { Text(name) },
                            onClick = {
                                selectedUnit = index
                                expanded = false
                                coroutineScope.launch {
                                    dataStore.edit { preferences ->
                                        preferences[preferredUnitKey] = index
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


//@Composable
//fun WindSpeedUnitSetting(context: Context) {
//    val dataStore = context.dataStore
//    val coroutineScope = rememberCoroutineScope()
//    val windUnitKey = intPreferencesKey("wind_unit")
//
//    var selectedWindUnit by remember { mutableStateOf(0) }
//    var expanded by remember { mutableStateOf(false) }
//
//    // Read value from DataStore
//    LaunchedEffect(dataStore) {
//        dataStore.data.map { preferences ->
//            preferences[windUnitKey] ?: 0
//        }.collect { value ->
//            selectedWindUnit = value
//        }
//    }
//
//    // UI
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(text = "Wind Speed Unit", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//
//        Box {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text(text = windUnits[selectedWindUnit], fontSize = 18.sp, fontWeight = FontWeight.Bold)
//                IconButton(onClick = { expanded = true }) {
//                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Dropdown Menu")
//                }
//
//                DropdownMenu(
//                    expanded = expanded,
//                    onDismissRequest = { expanded = false }
//                ) {
//                    windUnits.forEachIndexed { index, unit ->
//                        DropdownMenuItem(
//                            text = { Text(unit) },
//                            onClick = {
//                                selectedWindUnit = index
//                                expanded = false
//                                coroutineScope.launch {
//                                    dataStore.edit { preferences ->
//                                        preferences[windUnitKey] = index
//                                    }
//                                }
//                            }
//                        )
//                    }
//                }
//            }
//        }
//    }
//}


@Composable
fun PushNotificationsSetting(context: Context) {
    val dataStore = context.dataStore
    val coroutineScope = rememberCoroutineScope()
    val pushNotificationsKey = booleanPreferencesKey("push_notifications")

    var arePushNotificationsEnabled by remember { mutableStateOf(false) }

    // Read value from DataStore
    LaunchedEffect(dataStore) {
        dataStore.data.map { preferences ->
            preferences[pushNotificationsKey] ?: true
        }.collect { value ->
            arePushNotificationsEnabled = value
        }
    }

    // UI
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Push Notifications", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Switch(
            checked = arePushNotificationsEnabled,
            onCheckedChange = { newValue ->
                arePushNotificationsEnabled = newValue
                coroutineScope.launch {
                    dataStore.edit { preferences ->
                        preferences[pushNotificationsKey] = newValue
                    }
                }
            }
        )
    }
}


//@Preview (showBackground = true)
//@Composable
//fun PagePreview() {
//    WeatherSettingsPage(context = context)
//}

