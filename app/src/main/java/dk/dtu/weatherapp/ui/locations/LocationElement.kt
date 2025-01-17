package dk.dtu.weatherapp.ui.locations

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.dtu.weatherapp.models.Location
import dk.dtu.weatherapp.ui.theme.Typography


@Composable
fun LocationElement(
    location: Location,
    onToggleFavorite: (Location) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isFavorite by remember { mutableStateOf(location.isFavorite) }

    LaunchedEffect(location) {
        isFavorite = location.isFavorite
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = 4.dp)
    ) {
        IconButton(
            onClick = {
                isFavorite = !isFavorite
                onToggleFavorite(location)
            }
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite $isFavorite"
            )
        }

        /*Text(
            style = Typography.bodyLarge,
            textAlign = TextAlign.Right,
            text = "${location.temperature}°C",
            modifier = Modifier
                .padding(end = 32.dp)
                .weight(1f)
        )*/

        /*Icon(
            imageVector = ImageVector.vectorResource(id = location.iconURL),
            contentDescription = "Weather icon",
            Modifier
                .padding(start = 24.dp, end = 24.dp)
                .size(36.dp)
                .weight(1f)
        )*/

        Text(
            style = Typography.bodyLarge,
            text = location.name,
            lineHeight = 16.sp,
            modifier = Modifier
                .weight(2f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LocPrev() {

}
