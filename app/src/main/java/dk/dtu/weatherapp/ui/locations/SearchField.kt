package dk.dtu.weatherapp.ui.locations

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.ui.theme.Typography

@Composable
fun SearchField(onValueChange: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search icon",
                tint = Color.Unspecified,
                modifier = Modifier.size(36.dp)
            ) },
        maxLines = 1,
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)
        },
        label = { Box(
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Search for cities",
                style = Typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        } },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun SearchPrev() {
    //SearchField()
}