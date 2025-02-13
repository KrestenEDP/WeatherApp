package dk.dtu.weatherapp.ui.forecast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.GlobalUnits
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.models.Day
import dk.dtu.weatherapp.ui.theme.Typography

@Composable
fun InformationBoxes(forecast: Day, showMoreInformation: Boolean = false) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            InformationCard(
                title = "Sunrise",
                text = forecast.sunrise,
                icon = R.drawable.sunrise,
                unit = "",
                modifier = Modifier.weight(0.5f)
            )
            InformationCard(
                title = "Sunset",
                text = forecast.sunset,
                icon = R.drawable.sunset,
                unit = "",
                modifier = Modifier.weight(0.5f)
            )
        }
        if (showMoreInformation) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                InformationCard(
                    title = "Temperature",
                    text = "${forecast.dayTemp} / ${forecast.nightTemp}",
                    icon = R.drawable.thermostat,
                    unit = GlobalUnits.temp,
                    modifier = Modifier.weight(0.5f)
                )
                InformationCard(
                    title = "Wind",
                    text = "${forecast.windSpeed} (${forecast.windGusts})",
                    icon = R.drawable.wind,
                    unit = GlobalUnits.wind,
                    modifier = Modifier.weight(0.5f)
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            InformationCard(
                title = "Precipitation",
                text = forecast.precipitation.toString(),
                icon = R.drawable.i09d,
                unit = GlobalUnits.precipitation,
                modifier = Modifier.weight(0.5f)
            )
            InformationCard(
                title = "Humidity",
                text = forecast.humidity.toString(),
                icon = R.drawable.humidity,
                unit = "%",
                modifier = Modifier.weight(0.5f)
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            InformationCard(
                title = "Pressure",
                text = forecast.pressure.toString(),
                icon = R.drawable.compress,
                unit = "hPa",
                modifier = Modifier.weight(0.5f)
            )
            InformationCard(
                title = "Cloudiness",
                text = forecast.cloudiness.toString(),
                icon = R.drawable.i03d,
                unit = "%",
                modifier = Modifier.weight(0.5f)
            )
        }
    }
}

@Composable
fun InformationCard(title: String, text: String, icon: Int, unit: String, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = "null",
                modifier = Modifier
                    .size(16.dp)
            )
            Text(
                title,
                style = Typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        Text(
            text = "$text $unit",
            style = Typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        )
    }
}