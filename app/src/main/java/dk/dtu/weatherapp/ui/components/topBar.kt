package dk.dtu.weatherapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.navigation.Alerts
import dk.dtu.weatherapp.navigation.Stats
import dk.dtu.weatherapp.navigation.WeatherDestination
import dk.dtu.weatherapp.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        actions = {
            if (currentDestination == Stats) {
                val tooltipState = rememberTooltipState(isPersistent = true)
                TooltipBox(
                    positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
                    tooltip = {
                        RichTooltip(
                            title = { Text(
                                text = stringResource(R.string.statsTooltipTitle),
                                style = Typography.titleSmall
                            ) },
                            text = { Text(
                                text = stringResource(R.string.statsTooltipText),
                                style = Typography.bodyLarge
                            ) },
                            action = {
                                TextButton(
                                    onClick = { tooltipState.dismiss() }
                                ) {
                                    Text(
                                        text = stringResource(R.string.statsTooltipButton),
                                        style = Typography.bodyLarge
                                    )
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                        )
                    },
                    state = tooltipState,
                ) {
                    IconButton(
                        onClick = { CoroutineScope(Dispatchers.Main).launch { tooltipState.show() } }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Information about what to see on this page",
                            modifier = Modifier.size(40.dp)
                        )
                    }
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