package dk.dtu.weatherapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

interface WeatherDestination {
    val iconUnselected: ImageVector
    val iconSelected: ImageVector
    val route: String
    val appBarTitle: String
    val showBackButton: Boolean
}

object Weather : WeatherDestination {
    override val iconUnselected: ImageVector = Icons.Outlined.Home
    override val iconSelected: ImageVector = Icons.Filled.Home
    override val route: String = "weather"
    override val appBarTitle: String = "" // TODO: make dynamic
    override val showBackButton: Boolean = false
}

object Stats : WeatherDestination {
    override val iconUnselected: ImageVector = Icons.Outlined.Menu
    override val iconSelected: ImageVector = Icons.Filled.Menu
    override val route: String = "stats"
    override val appBarTitle: String = "Stats"
    override val showBackButton: Boolean = true
}

object Alerts : WeatherDestination {
    override val iconUnselected: ImageVector = Icons.Outlined.Warning
    override val iconSelected: ImageVector = Icons.Filled.Warning
    override val route: String = "alerts"
    override val appBarTitle: String = "Alerts"
    override val showBackButton: Boolean = true
}

object Settings : WeatherDestination {
    override val iconUnselected: ImageVector = Icons.Outlined.Settings
    override val iconSelected: ImageVector = Icons.Filled.Settings
    override val route: String = "settings"
    override val appBarTitle: String = "Settings"
    override val showBackButton: Boolean = true
}

object Locations : WeatherDestination {
    override val iconUnselected: ImageVector = Icons.Outlined.Home
    override val iconSelected: ImageVector = Icons.Filled.Home
    override val route: String = "locations"
    override val appBarTitle: String = "Locations"
    override val showBackButton: Boolean = true
}

object SingleDayForecast : WeatherDestination {
    override val iconUnselected: ImageVector = Icons.Outlined.Home
    override val iconSelected: ImageVector = Icons.Filled.Home
    override val route: String = "single_day_forecast"
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
val allScreens = listOf(Weather, Stats, Alerts, Settings, SingleDayForecast, Locations)