package dk.dtu.weatherapp.ui.map

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ScrollBar(){
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    Slider(
        value = sliderPosition,
        onValueChange = { sliderPosition = it },
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.secondary,
            activeTrackColor = MaterialTheme.colorScheme.secondary,
            inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        steps = 3,
        valueRange = 0f..50f,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ScrollbarPrev(){
ScrollBar()
}