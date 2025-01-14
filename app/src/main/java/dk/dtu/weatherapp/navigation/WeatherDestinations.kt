package dk.dtu.weatherapp.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import dk.dtu.weatherapp.R

interface WeatherDestination {
    val icon: Int
    val route: String
    val appBarTitle: String
    val showBackButton: Boolean
}

object Weather : WeatherDestination {
    override val icon: Int = R.drawable.home
    override val route: String = "weather"
    override val appBarTitle: String = ""
    override val showBackButton: Boolean = false
}

object Stats : WeatherDestination {
    override val icon: Int = R.drawable.stats
    override val route: String = "stats"
    override val appBarTitle: String = "Stats"
    override val showBackButton: Boolean = true
}

object Alerts : WeatherDestination {
    override val icon: Int = R.drawable.alerts
    override val route: String = "alerts"
    override val appBarTitle: String = "Alerts"
    override val showBackButton: Boolean = true
}

object Settings : WeatherDestination {
    override val icon: Int = R.drawable.settings
    override val route: String = "settings"
    override val appBarTitle: String = "Settings"
    override val showBackButton: Boolean = true
}

object Locations : WeatherDestination {
    override val icon: Int = R.drawable.home
    override val route: String = "locations"
    override val appBarTitle: String = "Locations"
    override val showBackButton: Boolean = true
}

object WeeklyForecast : WeatherDestination {
    override val icon: Int = R.drawable.home
    override val route: String = "weekly_forecast"
    override var appBarTitle by mutableStateOf("")
    override val showBackButton: Boolean = true

    const val INDEX = "index"
    val routeWithArgs = "$route/{$INDEX}"
    val arguments = listOf(
        navArgument(INDEX) { type = NavType.IntType }
    )
    val deepLinks = listOf(
        navDeepLink { uriPattern = "rally://$route/{$INDEX}" }
    )
}

val navBarScreens = listOf(Weather, Stats, Alerts, Settings)
val allScreens = listOf(Weather, Stats, Alerts, Settings, WeeklyForecast, Locations)