package dk.dtu.weatherapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dk.dtu.weatherapp.navigation.Settings
import dk.dtu.weatherapp.navigation.Weather
import dk.dtu.weatherapp.navigation.WeatherNavHost
import dk.dtu.weatherapp.navigation.allScreens
import dk.dtu.weatherapp.navigation.navBarScreens
import dk.dtu.weatherapp.navigation.navigateSingleTopTo
import dk.dtu.weatherapp.ui.components.MyTopAppBar
import dk.dtu.weatherapp.ui.components.NavBar
import dk.dtu.weatherapp.ui.theme.WeatherAppTheme
import androidx.compose.ui.platform.LocalContext



class WeatherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

@Composable
fun WeatherApp() {
    appContext = LocalContext.current.applicationContext
    WeatherAppTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val context = LocalContext.current

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