package dk.dtu.weatherapp.ui.locations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.models.Location
import dk.dtu.weatherapp.ui.theme.Typography

@Composable
fun LocationList(locations: List<Location>, type: LocationListType, modifier: Modifier = Modifier){
    Column(modifier = modifier.padding(16.dp)) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = type.icon,
                contentDescription = "Weather icon",
                Modifier
                    .size(36.dp)
            )
            Text(
                text = type.title,
                textAlign = TextAlign.Center,
                style = Typography.titleLarge,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }
        locations.forEach {
            LocationElement(it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecPrev() {
    //RecentView("Copenhagen", "London", "Paris")
}