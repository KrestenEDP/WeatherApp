package dk.dtu.weatherapp.ui.alerts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dk.dtu.weatherapp.models.Alert
import dk.dtu.weatherapp.ui.components.EmptyScreen
import dk.dtu.weatherapp.ui.components.LoadingScreen


@Composable
fun AlertsScreen(
    modifier: Modifier = Modifier
) {
    val alertViewModel: AlertsViewModel = viewModel()
    when (val alertsUIModel = alertViewModel.alertsUIState.collectAsState().value) {
        AlertsUIModel.Empty -> EmptyScreen(text = "Currently no weather alerts at your location")
        AlertsUIModel.Loading -> Column {
            LoadingScreen()
        }
        is AlertsUIModel.Data -> {
            AlertsContent(alertsUIModel.alerts, modifier)
        }
    }
}

@Composable
private fun AlertsContent(
    alerts: List<Alert>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(top = 16.dp)
    ) {
        items(alerts) { alert ->
            Alert(alert)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlertsScreenPrev() {
    AlertsScreen(modifier = Modifier.fillMaxSize())
}