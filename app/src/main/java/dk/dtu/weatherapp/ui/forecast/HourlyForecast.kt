package dk.dtu.weatherapp.ui.forecast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.GlobalUnits
import dk.dtu.weatherapp.models.Hour
import dk.dtu.weatherapp.ui.theme.Typography
import dk.dtu.weatherapp.ui.util.getPainterResource

fun LazyListScope.hourlyForecast(forecast: List<Hour>) {

    items(forecast.size) {
        ForecastElement(forecast[it])
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ForecastElement(hour: Hour) {
    FlowRow(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = hour.time,
                style = Typography.bodyLarge,
                textAlign = TextAlign.End,
                maxLines = 1,
                modifier = Modifier
            )
            Icon(
                imageVector = ImageVector.vectorResource(getPainterResource(hour.iconURL, LocalContext.current)),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(28.dp)
            )
        }
        Text(
            text = "${hour.temp} " + GlobalUnits.temp,
            style = Typography.bodyLarge,
            textAlign = TextAlign.End,
            maxLines = 1,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Text(
            text = "${hour.precipitation} " + GlobalUnits.precipitation,
            style = Typography.bodyLarge,
            textAlign = TextAlign.End,
            maxLines = 1,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier
                    .rotate(hour.windDegree.toFloat()+90)
            )
            Text(
                text = "${hour.wind} " + GlobalUnits.wind,
                style = Typography.bodyLarge,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier
            )
        }
    }
}