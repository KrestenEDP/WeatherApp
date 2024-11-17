package dk.dtu.weatherapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dk.dtu.weatherapp.navigation.Weather
import dk.dtu.weatherapp.navigation.WeatherDestination
import dk.dtu.weatherapp.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    currentDestination: WeatherDestination,
    navController: NavController
) {
    TopAppBar(
        title = {
            Text(
                text = currentDestination.appBarTitle,
                style = Typography.titleLarge,
                color = Color.White
            )
        },
        navigationIcon = {
            if (currentDestination.showBackButton) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            }
        },
        colors = topAppBarColors(
            containerColor = Color(0xFF6200EE),
            ),
    )
}

@Preview(showBackground = true)
@Composable
fun BarPreview() {
    val navController = rememberNavController()
    MyTopAppBar(Weather, navController)
}