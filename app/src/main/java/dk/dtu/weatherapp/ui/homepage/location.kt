package dk.dtu.weatherapp.ui.homepage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.ui.theme.Typography

@Composable
fun Location(
    onSearchClicked: () -> Unit,
    location: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 34.dp, top = 14.dp)
    ) {
        Icon(
            Icons.Default.Search,
            contentDescription = "Search button",
            Modifier
                .padding(0.dp)
                .width(40.dp)
                .height(40.dp)
                .clickable { onSearchClicked() }
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = location,
            style = Typography.titleLarge, // @TODO use larger font
            modifier = Modifier.clickable { onSearchClicked() }
        )
    }
}