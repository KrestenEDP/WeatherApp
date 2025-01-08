package dk.dtu.weatherapp.ui.alerts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.dtu.weatherapp.models.Alert

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
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(40.dp)
            )
            Text(
                text = alert.headline,
                fontSize = 24.sp
            )
        }
        Text(
            text = alert.event,
            fontSize = 18.sp
        )
        Text(
            text = "Severity: " + alert.severity + ", Urgency: " + alert.urgency,
            color = Color.DarkGray,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = alert.description)
    }
}
