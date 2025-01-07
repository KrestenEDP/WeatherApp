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
import dk.dtu.weatherapp.Firebase.FirebaseHelper
import dk.dtu.weatherapp.Firebase.GetMacAddress
import dk.dtu.weatherapp.models.Location
import dk.dtu.weatherapp.ui.theme.Typography
import androidx.compose.ui.platform.LocalContext


@Composable
fun LocationElement(location: Location, modifier: Modifier = Modifier) {
    var isFavorite by remember { mutableStateOf(false) }

    val userId = GetMacAddress(LocalContext.current) ?: "unknownUserId"

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        IconButton(
            onClick = {
                isFavorite = !isFavorite

                if (isFavorite) {
                    // Save to Firebase
                    FirebaseHelper.saveFavoriteToFirestore(
                        userId = userId,
                        cityName = location.name,
                        onSuccess = {
                            println("City added to favorites successfully!")
                        },
                        onFailure = { exception ->
                            println("Error saving city: ${exception.message}")
                        }
                    )
                } else {
                    // Remove from Firebase
                    FirebaseHelper.removeFavoriteFromFirestore(
                        userId = userId,
                        cityName = location.name,
                        onSuccess = {
                            println("City removed from favorites successfully!")
                        },
                        onFailure = { exception ->
                            println("Error removing city: ${exception.message}")
                        }
                    )
                }
            }
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite icon"
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
            imageVector = ImageVector.vectorResource(id = location.iconURL), // @TODO icon has to change when weather changes (switch case)
            // @TODO change icons to real weather icons
            contentDescription = "Weather icon",
            Modifier
                .padding(start = 24.dp, end = 24.dp) //@TODO align icons to center
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
    /*Column {
        LocationList("Copenhagen", 23.5, "sunny")
        LocationList("james", 200.0, "sunny")
        LocationList("cuba", -40.0, "sunny")
    }*/
}
