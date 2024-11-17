package dk.dtu.weatherapp.ui.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.ui.theme.Typography

@Composable
fun InfoBox(Weekday: String, Date: String){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(bottom = 32.dp)
    )
    {
        ScrollBar()
        Text(
            Weekday,
            textAlign = TextAlign.Center,
            style = Typography.titleLarge,
            modifier = Modifier
        )
        Text(
            Date,
            textAlign = TextAlign.Center,
            style = Typography.titleLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun james(){
    InfoBox("Saturday", "16. November")
}