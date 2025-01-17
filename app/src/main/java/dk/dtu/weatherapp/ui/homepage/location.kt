package dk.dtu.weatherapp.ui.homepage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.ui.theme.Typography

@Composable
fun Location(
    onSearchClicked: () -> Unit,
    location: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Icon(
            Icons.Default.Search,
            contentDescription = "Search button",
            modifier = Modifier
                .padding(0.dp)
                .size(36.dp)
                .clickable { onSearchClicked() }
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = location,
            style = Typography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .clickable { onSearchClicked() }
                .padding(end = 36.dp)
        )
    }
}