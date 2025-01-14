package dk.dtu.weatherapp.ui.forecast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.GlobalUnits
import dk.dtu.weatherapp.models.Hour
import dk.dtu.weatherapp.ui.theme.Typography
import dk.dtu.weatherapp.ui.util.getPainterResource

fun LazyListScope.HourlyForecast(forecast: List<Hour>) {

    items(forecast.size) {
        ForecastElement(forecast[it])
    }
}

@Composable
fun ForecastElement(hour: Hour) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = hour.time,
            style = Typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(PaddingValues(start = 8.dp))
                .weight(1.5f)
        )

        Icon(
            imageVector = ImageVector.vectorResource(getPainterResource(hour.iconURL, LocalContext.current)),
            contentDescription = null, // TODO: Add weather type as content description
            modifier = Modifier
                .padding(PaddingValues(start = 8.dp, end = 8.dp))
                .weight(1f)
        )
        Text(
            text = "${hour.temp} " + GlobalUnits.temp,
            style = Typography.bodyLarge,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(PaddingValues(8.dp))
                .weight(1.8f)
        )
        Text(
            text = "${hour.precipitation} " + GlobalUnits.precipitation,
            style = Typography.bodyLarge,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(PaddingValues(8.dp))
                .weight(2f)
        )
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .weight(2.8f)
                .padding(PaddingValues(8.dp))
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null, // TODO: Add more options as content description
                modifier = Modifier
                    .padding(start=8.dp, end=0.dp)
                    .rotate(hour.windDegree.toFloat()+90)
            )
            Text(
                text = "${hour.wind} " + GlobalUnits.wind,
                style = Typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxSize()
            )
        }
    }
}