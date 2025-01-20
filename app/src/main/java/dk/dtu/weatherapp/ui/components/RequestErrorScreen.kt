package dk.dtu.weatherapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.ui.theme.Typography

@Composable
fun RequestErrorScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Couldn't retrieve data from server and no data in cache, please check your internet connection",
            style = Typography.bodyLarge
        )
    }
}