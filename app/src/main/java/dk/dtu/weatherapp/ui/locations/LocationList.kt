
package dk.dtu.weatherapp.ui.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.models.Location
import dk.dtu.weatherapp.ui.components.NoDataScreen
import dk.dtu.weatherapp.ui.components.LoadingScreen
import dk.dtu.weatherapp.ui.components.RequestErrorScreen
import dk.dtu.weatherapp.ui.theme.Typography


@Composable
fun LocationList(
    locationsUIModel: LocationsUIModel,
    type: LocationListType,
    modifier: Modifier = Modifier,
    onToggleFavorite: (Location) -> Unit = {},
    onLocationClicked: (Location) -> Unit = {},
) {
    Column(modifier = modifier.padding(16.dp)) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(type.icon),
                contentDescription = "Weather icon",
                modifier = Modifier.size(36.dp)
            )
            Text(
                text = type.title,
                textAlign = TextAlign.Center,
                style = Typography.titleLarge,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        when (locationsUIModel) {
            LocationsUIModel.Empty -> NoDataScreen(type.noElementsText)
            LocationsUIModel.Loading -> LoadingScreen()
            LocationsUIModel.RequestError -> RequestErrorScreen()
            is LocationsUIModel.Data -> {
                locationsUIModel.locations.forEach { location ->
                    LocationElement(
                        location = location,
                        onToggleFavorite = onToggleFavorite,
                        modifier = Modifier.clickable(onClick = { onLocationClicked(location) })
                    )
                }
            }
        }
    }
}
