package dk.dtu.weatherapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.navigation.WeatherDestination
import dk.dtu.weatherapp.ui.theme.Typography
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
                        imageVector = ImageVector.vectorResource(screen.icon),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                },
                label = {
                    Text(
                        style = Typography.bodyLarge,
                        text = screen.route.replaceFirstChar { it.titlecase(Locale.ENGLISH) },
                        maxLines = 1,
                        ) },
                selected = currentScreen == screen,
                onClick = {
                    onTabSelected(screen)
                }
            )
        }
    }
}
