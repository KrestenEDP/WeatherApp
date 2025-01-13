package dk.dtu.weatherapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dk.dtu.weatherapp.GlobalUnits.edgymode
import dk.dtu.weatherapp.GlobalUnits.noticeme
import dk.dtu.weatherapp.domain.dataStore
import dk.dtu.weatherapp.navigation.Settings
import dk.dtu.weatherapp.navigation.Weather
import dk.dtu.weatherapp.navigation.WeatherNavHost
import dk.dtu.weatherapp.navigation.allScreens
import dk.dtu.weatherapp.navigation.navBarScreens
import dk.dtu.weatherapp.navigation.navigateSingleTopTo
import dk.dtu.weatherapp.ui.alerts.createNotificationChannel
import dk.dtu.weatherapp.ui.components.MyTopAppBar
import dk.dtu.weatherapp.ui.components.NavBar
import dk.dtu.weatherapp.ui.theme.WeatherAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WeatherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeGlobalUnits(this)
        enableEdgeToEdge()
        setContent {
            WeatherApp()
        }
    }
}

private var appContext: Context? = null
fun getAppContext(): Context {
    return appContext!!
}

object GlobalUnits {
    var temp: String = ""
    var wind: String = ""
    var precipitation: String = ""
    var edgymode: Boolean = true
    var noticeme: Boolean = true
}

private fun initializeGlobalUnits(context: Context) {
    CoroutineScope(Dispatchers.Default).launch {
        val unitSetting = getUnitSetting(context)
        val darkSetting = getDarkSetting(context)
        val pushSetting = getPushSetting(context)
        GlobalUnitUtils.updateGlobalUnits(unitSetting)
        edgymode = darkSetting
        noticeme = pushSetting
    }
}

suspend fun getUnitSetting(context: Context): Int {
    val dataStore = context.dataStore.data
    val preferredUnitKey = intPreferencesKey("preferred_unit")

    return dataStore.map { preferences ->
        preferences[preferredUnitKey] ?: 0
    }.first()
}

suspend fun getDarkSetting(context: Context): Boolean {
    val dataStore = context.dataStore.data
    val preferredBooleanKey = booleanPreferencesKey("dark_mode")

    return dataStore.map { preferences ->
        preferences[preferredBooleanKey] ?: true
    }.first()
}

suspend fun getPushSetting(context: Context): Boolean {
    val dataStore = context.dataStore.data
    val preferredPushKey = booleanPreferencesKey("push_notifications")

    return dataStore.map { preferences ->
        preferences[preferredPushKey] ?: true
    }.first()
}

object GlobalUnitUtils {
    fun updateGlobalUnits(unitSetting: Int) {
        when (unitSetting) {
            0 -> { // Metric
                GlobalUnits.temp = "°C"
                GlobalUnits.wind = "m/s"
                GlobalUnits.precipitation = "mm"
            }
            1 -> { // Imperial
                GlobalUnits.temp = "°F"
                GlobalUnits.wind = "mph"
                GlobalUnits.precipitation = "in"
            }
            else -> { // Kelvin
                GlobalUnits.temp = "°K"
                GlobalUnits.wind = "m/s"
                GlobalUnits.precipitation = "mm"
            }
        }
    }
}


@Composable
fun WeatherApp() {
    appContext = LocalContext.current.applicationContext

    WeatherAppTheme(edgymode) {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val context = LocalContext.current
        createNotificationChannel(context)

        val currentScreen =
            allScreens.find {
                currentDestination?.route?.contains(it.route) == true
            } ?: Settings

        val navBarDestination =
            navBarScreens.find { it.route == currentDestination?.route } ?: Weather

        Scaffold(
            topBar = {
                MyTopAppBar(currentScreen, navController)
            },
            bottomBar = {
                NavBar(
                    allScreens = navBarScreens,
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(newScreen.route)
                    },
                    currentScreen = navBarDestination
                )
            }
        ) { innerPadding ->
            WeatherNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                context = context
            )
        }
    }
}