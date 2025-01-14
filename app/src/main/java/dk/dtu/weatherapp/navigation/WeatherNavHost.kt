package dk.dtu.weatherapp.navigation

import android.content.Context
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dk.dtu.weatherapp.domain.setCurrentLocation
import dk.dtu.weatherapp.ui.alerts.AlertsScreen
import dk.dtu.weatherapp.ui.forecast.WeeklyForecastScreen
import dk.dtu.weatherapp.ui.homepage.Homepage
import dk.dtu.weatherapp.ui.locations.LocationPage
import dk.dtu.weatherapp.ui.settings.WeatherSettingsPage
import dk.dtu.weatherapp.ui.statistics.StatisticScreen

@Composable
fun WeatherNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    context: Context
) {

    NavHost(
        navController = navController,
        startDestination = Weather.route,
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    50, 50, easing = EaseIn
                )
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    50, 50, easing = EaseOut
                )
            )
        },
        modifier = modifier,
    ) {
        composable(route = Weather.route) {
            Homepage(
                onDayClicked = { singleDayIndex ->
                    navController.navigateToWeeklyForecast(singleDayIndex)
                },
                onSearchClicked = {
                    navController.navigateSingleTopTo(Locations.route)
                }
            )
        }
        composable(route = Stats.route) {
            StatisticScreen()
        }
        composable(route = Alerts.route) {
            AlertsScreen()
        }
        composable(route = Settings.route) {
            WeatherSettingsPage(context)
        }
        composable(route = Locations.route) {
            LocationPage(onLocationClicked = {
                setCurrentLocation(it)
                navController.navigateSingleTopTo(Weather.route)
            })
        }
        composable(
            route = WeeklyForecast.routeWithArgs,
            arguments = WeeklyForecast.arguments,
            deepLinks = WeeklyForecast.deepLinks
        ) { navBackStackEntry ->
            val singleDayIndex =
                navBackStackEntry.arguments?.getInt(WeeklyForecast.INDEX)
            WeeklyForecastScreen(singleDayIndex)
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String, restore: Boolean = true) {
    this.navigate(route) {
        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = restore
    }
}

private fun NavHostController.navigateToWeeklyForecast(index: Int) {
    this.navigateSingleTopTo("${WeeklyForecast.route}/$index", restore = false)
}