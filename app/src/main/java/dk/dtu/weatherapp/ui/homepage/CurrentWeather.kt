package dk.dtu.weatherapp.ui.homepage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.GlobalUnits
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.models.Current
import dk.dtu.weatherapp.ui.theme.Typography
import dk.dtu.weatherapp.ui.util.getPainterResource

@Composable
fun CurrentWeather(data: Current) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(getPainterResource(data.icon, LocalContext.current)),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text("${data.temp} " + GlobalUnits.temp, style = Typography.titleLarge)
            Text("Feels like ${data.chill} " + GlobalUnits.temp,
                style = Typography.bodyLarge,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painterResource(R.drawable.wind), // @TODO maybe change to arrow to show direction
                    contentDescription = "Current wind strength",
                    modifier = Modifier.size(20.dp)
                )
                Text("${data.wind} " + GlobalUnits.wind, style = Typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painterResource(R.drawable.i09d),
                    contentDescription = "Current rain fall",
                    modifier = Modifier.size(20.dp)
                )
                Text("${data.precipitation} " + GlobalUnits.precipitation, style = Typography.titleMedium)
            }
        }
    }
}

@Preview
@Composable
fun CurrentPrev() {
    CurrentWeather(Current(17.0, 14.0, 12.0, 2.2, "i02d"))
}