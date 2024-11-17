package dk.dtu.weatherapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.navigation.WeatherDestination
import java.util.Locale


@Composable fun NavBar(
    allScreens: List<WeatherDestination>,
    onTabSelected: (WeatherDestination) -> Unit,
    currentScreen: WeatherDestination
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        allScreens.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (screen == currentScreen) screen.iconSelected else screen.iconUnselected,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )
                },
                label = { Text(screen.route.replaceFirstChar { it.titlecase(Locale.getDefault()) }) },
                selected = currentScreen == screen,
                onClick = {
                    onTabSelected(screen)
                }
            )
        }
    }
}
