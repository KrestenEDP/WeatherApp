package dk.dtu.weatherapp.ui.map

import android.icu.text.Transliterator.Position
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier

@Composable
fun MapScreen(){
    Column {
        Row(modifier = Modifier
            .weight(1f)
        ) {
            MapImage()
        }
        Row {
            InfoBox("Saturday","16. November")
        }
    }
}

@Preview(showBackground = true)
@Composable

fun juan(){
    MapScreen()
}
