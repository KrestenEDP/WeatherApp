package dk.dtu.weatherapp.ui.locations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LocationPage(){
   Column(Modifier.fillMaxHeight()){
        SearchField()
        FavoriteView("Copenhagen", "London", "Paris")
        RecentView("Lisbon", "Stockholm", "Oslo")
    }
}

@Preview(showBackground = true)
@Composable
fun LocationPagePrev() {
    LocationPage()
}