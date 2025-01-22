package dk.dtu.weatherapp.ui.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.models.StatsDay
import dk.dtu.weatherapp.ui.components.LoadingScreen
import dk.dtu.weatherapp.ui.components.NoDataScreen
import dk.dtu.weatherapp.ui.components.RequestErrorScreen
import dk.dtu.weatherapp.ui.theme.Typography
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.PopupProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YearStats(statsViewModel: StatsViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val options: List<String> = listOf("Temperature", "Precipitation", "Wind speed", "Pressure", "Humidity", "Cloudiness")
    val textFieldState = remember { mutableStateOf(options[0]) }

    LaunchedEffect(Unit) { statsViewModel.getYearStats() }

    Column {
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it })
        {
            OutlinedTextField(
                readOnly = true,
                value = textFieldState.value,
                onValueChange = {},
                label = { Text("Select parameter", style = MaterialTheme.typography.bodyLarge) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = OutlinedTextFieldDefaults.colors(),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, style = MaterialTheme.typography.bodyLarge) },
                        onClick = {
                            textFieldState.value = option
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        when (val statsYearUIModel = statsViewModel.yearUIState.collectAsState().value) {
            StatsYearUIModel.RequestError -> RequestErrorScreen()
            StatsYearUIModel.Empty -> NoDataScreen("No available data from the server")
            StatsYearUIModel.Loading -> LoadingScreen()
            is StatsYearUIModel.Data -> {
                val stats = statsYearUIModel.months
                key(textFieldState.value) {
                    StatsChart(textFieldState.value, stats)
                }
            }
        }
    }
}

@Composable
fun StatsChart(textFieldState: String, stats: List<StatsDay>) {
    val monthList = listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )
    LineChart(
        modifier = Modifier.padding(8.dp),
        data = remember {
            listOf(
                Line(
                    label = "Average",
                    values = stats.map {
                        when (textFieldState) {
                            "Temperature" -> it.temp.mean
                            "Precipitation" -> it.precipitation.mean
                            "Wind speed" -> it.wind.mean
                            "Pressure" -> it.pressure.mean
                            "Humidity" -> it.humidity.mean
                            "Cloudiness" -> it.clouds.mean
                            else -> 0.0
                        }
                    },
                    color = SolidColor(Color.Cyan),
                    curvedEdges = false,
                    dotProperties = DotProperties(
                        enabled = true,
                        color = SolidColor(Color.White),
                        strokeWidth = 2.dp,
                        radius = 3.dp,
                        strokeColor = SolidColor(Color.Blue),
                    )
                ),
            )
        },
        labelHelperProperties = LabelHelperProperties(
            enabled = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
        ),
        labelProperties = LabelProperties(
            enabled = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
            labels = monthList,
            rotation = LabelProperties.Rotation(
                mode = LabelProperties.Rotation.Mode.IfNecessary,
                degree = 25f
            ),
            builder = {modifier,label,_,_->
                Text(modifier=modifier,text=label, style = Typography.labelSmall)
            }
        ),
        indicatorProperties = HorizontalIndicatorProperties(
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
            contentBuilder = { value->
                if (textFieldState == "Precipitation") "%.2f".format(value)
                else "%.1f".format(value)
            }
        ),
        popupProperties = PopupProperties(
            enabled = true,
            textStyle = MaterialTheme.typography.labelSmall.copy(
                color = Color.White
            ),
            contentBuilder = { value->
                if (textFieldState == "Precipitation") "%.2f".format(value)
                else "%.1f".format(value)
            }
        )
    )
}