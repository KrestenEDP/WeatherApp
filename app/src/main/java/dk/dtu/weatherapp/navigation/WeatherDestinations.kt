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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

interface WeatherDestination {
    val iconUnselected: ImageVector
    val iconSelected: ImageVector
    val route: String
}

object Weather : WeatherDestination {
    override val iconUnselected: ImageVector = Icons.Outlined.Home
    override val iconSelected: ImageVector = Icons.Filled.Home
    override val route: String = "weather"
}

object Map : WeatherDestination {
    override val iconUnselected: ImageVector = Icons.Outlined.Menu
    override val iconSelected: ImageVector = Icons.Filled.Menu
    override val route: String = "map"
}

object Alerts : WeatherDestination {
    override val iconUnselected: ImageVector = Icons.Outlined.Warning
    override val iconSelected: ImageVector = Icons.Filled.Warning
    override val route: String = "alerts"
}

object Settings : WeatherDestination {
    override val iconUnselected: ImageVector = Icons.Outlined.Settings
    override val iconSelected: ImageVector = Icons.Filled.Settings
    override val route: String = "settings"
}

object Locations : WeatherDestination {
    override val iconUnselected: ImageVector = Icons.Outlined.Home
    override val iconSelected: ImageVector = Icons.Filled.Home
    override val route: String = "locations"
}

object SingleDayForecast : WeatherDestination {
    override val iconUnselected: ImageVector = Icons.Outlined.Home
    override val iconSelected: ImageVector = Icons.Filled.Home
    override val route: String = "singe_day_forecast"

    const val singleDayIndex = "index"
    val routeWithArgs = "$route/{$singleDayIndex}"
    val arguments = listOf(
        navArgument(singleDayIndex) { type = NavType.IntType }
    )
    val deepLinks = listOf(
        navDeepLink { uriPattern = "rally://$route/{$singleDayIndex}" }
    )
}

val navBarScreens = listOf(Weather, Map, Alerts, Settings)