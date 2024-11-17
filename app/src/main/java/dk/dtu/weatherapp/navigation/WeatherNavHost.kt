package dk.dtu.weatherapp.navigation

import android.util.Log
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.ui.alerts.AlertsScreen
import dk.dtu.weatherapp.ui.forecast.SingleDayForecastScreen
import dk.dtu.weatherapp.ui.homepage.Homepage
import dk.dtu.weatherapp.ui.locations.LocationPage
import dk.dtu.weatherapp.ui.map.MapScreen
import dk.dtu.weatherapp.ui.settings.WeatherSettingsPage

@Composable
fun WeatherNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Weather.route,
        modifier = modifier,
    ) {
        composable(route = Weather.route) {
            Homepage(
                onDayClicked = { singleDayIndex ->
                    navController.navigateToSingleDayForecast(singleDayIndex)
                },
                onSearchClicked = {
                    navController.navigateSingleTopTo(Locations.route)
                }
            )
        }
        composable(route = Map.route) {
            MapScreen()
        }
        composable(route = Alerts.route) {
            AlertsScreen()
        }
        composable(route = Settings.route) {
            WeatherSettingsPage()
        }
        composable(route = Locations.route) {
            LocationPage()
        }
        composable(
            route = SingleDayForecast.routeWithArgs,
            arguments = SingleDayForecast.arguments,
            deepLinks = SingleDayForecast.deepLinks
        ) { navBackStackEntry ->
            val singleDayIndex =
                navBackStackEntry.arguments?.getInt(SingleDayForecast.singleDayIndex)
            SingleDayForecastScreen(singleDayIndex)
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) {
    this.navigate(route) {
        /*popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
            saveState = true
        }*/
        launchSingleTop = true
        //restoreState = true
        anim {
            enter = R.anim.nav_default_enter_anim
            exit = R.anim.nav_default_exit_anim
            popEnter = R.anim.nav_default_pop_enter_anim
            popExit = R.anim.nav_default_pop_exit_anim
        }
    }
}

private fun NavHostController.navigateToSingleDayForecast(index: Int) {
    this.navigateSingleTopTo("${SingleDayForecast.route}/$index")
}