package dk.dtu.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dk.dtu.weatherapp.navigation.Settings
import dk.dtu.weatherapp.navigation.SingleDayForecast
import dk.dtu.weatherapp.navigation.Weather
import dk.dtu.weatherapp.navigation.WeatherNavHost
import dk.dtu.weatherapp.navigation.allScreens
import dk.dtu.weatherapp.navigation.navBarScreens
import dk.dtu.weatherapp.navigation.navigateSingleTopTo
import dk.dtu.weatherapp.ui.components.MyTopAppBar
import dk.dtu.weatherapp.ui.components.NavBar
import dk.dtu.weatherapp.ui.theme.WeatherAppTheme

class WeatherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherApp()
        }
    }
}

@Composable
fun WeatherApp() {
    WeatherAppTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        println("Current destination: ${currentDestination?.route}")
        allScreens.forEach { println("Available screen route: ${it.route}") }

        val currentScreen =
            allScreens.find { it.route == currentDestination?.route } ?: Settings


        println("Current Screen after: ${currentScreen.route}")

        val navBarDestination =
            navBarScreens.find { it.route == currentDestination?.route } ?: Weather

        Scaffold(
            topBar = {
                if (currentDestination?.route != "weather") {
                    MyTopAppBar(currentScreen, navController)
                }
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
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}