package dk.dtu.weatherapp.ui.settings

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import dk.dtu.weatherapp.GlobalUnitUtils
import dk.dtu.weatherapp.GlobalUnits.edgymode
import dk.dtu.weatherapp.domain.dataStore
import dk.dtu.weatherapp.ui.theme.Typography
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val unitNames= listOf(
    "Celsius (°C) | meters/sec", "Fahrenheit (°F) | miles/hour", "Kelvin (°K) | meters/sec"
)

private val Units = listOf(
    "Metric", "Imperial", "Scientific"
)


@Composable
fun WeatherSettingsPage(context: Context) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
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
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Dark Mode", style = Typography.titleSmall)

        Switch(
            checked = isDarkModeEnabled,
            onCheckedChange = { newValue ->
                isDarkModeEnabled = newValue
                coroutineScope.launch {
                    dataStore.edit { preferences ->
                        preferences[darkModeKey] = newValue
                    }
                }


                edgymode = newValue

                // Delay or chaos
                if (context is Activity) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        context.recreate()
                    }, 100)
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
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Dynamic Background",
            style = Typography.titleSmall
        )
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
            .height(48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Unit", style = Typography.titleSmall)

        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .fillMaxHeight()
                    .clickable { expanded = true }
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = Units[selectedUnit],
                    style = Typography.titleSmall)
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = "Dropdown Menu",
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    unitNames.forEachIndexed { index, name ->
                        DropdownMenuItem(
                            text = { Text(name, style = Typography.titleSmall) },
                            onClick = {
                                selectedUnit = index
                                expanded = false
                                GlobalUnitUtils.updateGlobalUnits(index)
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
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Push Notifications", style = Typography.titleSmall)

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

