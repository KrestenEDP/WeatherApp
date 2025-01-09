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
import dk.dtu.weatherapp.domain.LocationRepository
import androidx.lifecycle.viewmodel.compose.viewModel



@Composable
fun LocationElement(
    location: Location,
    modifier: Modifier = Modifier,
    locationViewModel: LocationsViewModel
) {
    var isFavorite by remember { mutableStateOf(false) }
    val userId = GetMacAddress(LocalContext.current) ?: "unknownUserId"

    // Check if the location is already a favorite from Firebase (simulating with isFavorite flag)
    FirebaseHelper.isFavoriteInFirestore(
        userId = userId,
        cityName = location.name,
        onSuccess = { favorite ->
            isFavorite = favorite
        },
        onFailure = { exception ->
            println("Error checking favorite status: ${exception.message}")
        }
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        IconButton(
            onClick = {
                isFavorite = !isFavorite

                if (isFavorite) {
                    FirebaseHelper.saveFavoriteToFirestore(
                        userId = userId,
                        cityName = location.name,
                        onSuccess = {
                            //locationViewModel.addFavorite(location) // Use the passed ViewModel to add favorite
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
                            //locationViewModel.removeFavorite(location) // Use the passed ViewModel to remove favorite
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

        // The rest of your UI for displaying location
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
