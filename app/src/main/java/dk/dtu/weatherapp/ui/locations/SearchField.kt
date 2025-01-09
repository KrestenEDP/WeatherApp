package dk.dtu.weatherapp.ui.locations

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchField(locationsViewModel: LocationsViewModel) {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search icon",
                Modifier
                    .size(36.dp)
            )
                      },
        value = text,
        onValueChange = {
            text = it
            locationsViewModel.search(it)
        },
        label = { Text("Search for cities") },
        modifier = Modifier
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun SearchPrev() {
    //SearchField()
}