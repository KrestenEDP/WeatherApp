package dk.dtu.weatherapp.ui.locations

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import dk.dtu.weatherapp.ui.theme.Typography

@Composable
fun FavoriteView(
    fav1: String,
    fav2: String,
    fav3: String,
    modifier: Modifier = Modifier
){
    Column(modifier = Modifier
        .padding(16.dp)) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(bottom = 8.dp)
        ) {
            Icon(
                Icons.Default.Favorite,
                contentDescription = "Weather icon",
                Modifier
                    .size(36.dp)
            )
            Text(
                text = "Favorites",
                textAlign = TextAlign.Center,
                style = Typography.titleLarge,
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        }
        Locationlist(fav1, 23.5, "sunny") //TODO make database or cash with user data, such as favorite locations
        Locationlist(fav2, 200.0, "hail")
        Locationlist(fav3, -40.3, "sunny")
    }
}

@Preview(showBackground = true)
@Composable
fun FavPrev() {
    FavoriteView("Copenhagen", "London", "Paris")
}