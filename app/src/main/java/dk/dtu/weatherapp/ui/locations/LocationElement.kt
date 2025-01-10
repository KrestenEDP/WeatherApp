package dk.dtu.weatherapp.ui.locations

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.models.Location
import dk.dtu.weatherapp.ui.theme.Typography


@Composable
fun LocationElement(
    location: Location,
    onToggleFavorite: (Location) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isFavorite by remember { mutableStateOf(location.isFavorite) }
    //val userId = GetMacAddress(LocalContext.current) ?: "unknownUserId"

    /*FirebaseHelper.isFavoriteInFirestore(
        userId = userId,
        cityName = location.name,
        onSuccess = { favorite ->
            isFavorite = favorite
        },
        onFailure = { exception ->
            println("Error checking favorite status: ${exception.message}")
        }
    )*/

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        IconButton(
            onClick = {
                location.isFavorite = !location.isFavorite
                isFavorite = location.isFavorite
                onToggleFavorite(location)
                //isFavorite = !isFavorite

                /*if (isFavorite) {
                    FirebaseHelper.saveFavoriteToFirestore(
                        userId = userId,
                        cityName = location.name,
                        onSuccess = {
                            onToggleFavorite(location)
                            //locationViewModel.addFavorite(location)
                        },
                        onFailure = { exception ->
                            println("Error saving city: ${exception.message}")
                        }
                    )
                } else {
                    FirebaseHelper.removeFavoriteFromFirestore(
                        userId = userId,
                        cityName = location.name,
                        onSuccess = {
                            onToggleFavorite(location)
                            //locationViewModel.removeFavorite(location)
                        },
                        onFailure = { exception ->
                            println("Error removing city: ${exception.message}")
                        }
                    )
                }*/
            }
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite $isFavorite"
            )
        }

        Text(
            style = Typography.bodyLarge,
            textAlign = TextAlign.Right,
            text = "${location.temperature}°C",
            modifier = Modifier
                .padding(end = 32.dp)
                .weight(1f)
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = location.iconURL),
            contentDescription = "Weather icon",
            Modifier
                .padding(start = 24.dp, end = 24.dp)
                .size(36.dp)
                .weight(1f)
        )

        Text(
            style = Typography.bodyLarge,
            text = location.name,
            modifier = Modifier
                .weight(2f)
        )
    }
}






@Preview(showBackground = true)
@Composable
fun LocPrev() {

}
