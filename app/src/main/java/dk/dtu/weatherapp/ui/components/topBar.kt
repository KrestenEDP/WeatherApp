package dk.dtu.weatherapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dk.dtu.weatherapp.navigation.Alerts
import dk.dtu.weatherapp.navigation.WeatherDestination
import dk.dtu.weatherapp.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopAppBar(
    currentDestination: WeatherDestination,
    navController: NavController
) {
    TopAppBar(
        title = {
            Text(
                text = currentDestination.appBarTitle,
                style = Typography.headlineLarge,
            )
        },
        navigationIcon = {
            if (currentDestination.showBackButton) {
                IconButton(
                    onClick = { navController.navigateUp() }
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(28.dp)
                            //.padding(start = 4.dp)
                    )
                }
            }
        },
        modifier = Modifier.padding(top = 8.dp, start = 4.dp, bottom = 0.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    val navController = rememberNavController()
    WeatherTopAppBar(Alerts, navController)
}