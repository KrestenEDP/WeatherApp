package dk.dtu.weatherapp.ui.statistics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.Line

@Composable
fun YearStats(statsViewModel: StatsViewModel) {
    LineChart(
        data = remember {
            listOf(
                Line(
                    label = "Test",
                    values = listOf(2.0, 3.0, 4.0),
                    color = SolidColor(Color.Blue),
                    curvedEdges = false,
                    dotProperties = DotProperties(
                        enabled = true,
                        color = SolidColor(Color.White),
                        strokeWidth = 4.dp,
                        radius = 7.dp,
                        strokeColor = SolidColor(Color.Blue),
                    )
                )
            )
        }
    )
}