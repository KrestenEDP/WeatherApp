package dk.dtu.weatherapp.ui.alerts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.models.Alert
import dk.dtu.weatherapp.ui.theme.Typography

@Composable
fun Alert(alert: Alert, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(start = 16.dp, end = 16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(bottom = 4.dp).padding(end = 16.dp)
        ) {
            Icon(
                Icons.Filled.Warning,
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(36.dp)
            )
            Text(
                text = alert.headline,
                style = Typography.titleSmall
            )
        }
        Text(
            text = alert.event,
            style = Typography.bodyLarge,
        )
        Text(
            text = "Severity: " + alert.severity + ", Urgency: " + alert.urgency,
            color = MaterialTheme.colorScheme.secondary,
            style = Typography.bodyLarge
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = alert.description, style = Typography.bodyLarge)
    }
}

@Preview
@Composable
fun AlertPreview() {
    Alert(
        Alert(
            headline = "Varsel om stor sneskredsfare i region Svartisen 15.01.2025",
            event = "Frost warning in effect from 10pm to 6am",
            severity = "Minor",
            urgency = "Expected",
            description = "Temperatures are expected to drop below freezing tonight. Take precautions to protect sensitive plants."
        )
    )
}
