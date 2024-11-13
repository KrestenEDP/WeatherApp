package dk.dtu.weatherapp.ui.locations

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
@Composable
fun SearchField() {
    var text by remember { mutableStateOf("") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = 32.dp)
    ) {
        Icon(
            Icons.Default.Search, // @TODO change icon when added to favorites
            contentDescription = "Search icon",
            Modifier
                .padding(start = 16.dp)
                .size(36.dp)
                .align(Alignment.CenterVertically)// TODO fix offset of icon
        )
        OutlinedTextField(
            value = text,
            onValueChange = {text = it},
            label = { Text("Search") },
            modifier = Modifier
                .padding(start = 16.dp)

        )
    }

}

@Preview(showBackground = true)
@Composable
fun SearchPrev() {
    SearchField()
}