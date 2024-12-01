package dk.dtu.weatherapp.ui.locations

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

interface LocationListType {
    val icon: ImageVector
    val title: String
}

object AllLocations : LocationListType {
    override val icon: ImageVector = Icons.Default.LocationOn
    override val title: String = "Recent"
}

object FavouriteLocations : LocationListType {
    override val icon: ImageVector = Icons.Default.Favorite
    override val title: String = "Favorites"
}