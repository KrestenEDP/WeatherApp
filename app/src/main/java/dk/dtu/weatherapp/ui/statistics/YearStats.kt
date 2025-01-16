package dk.dtu.weatherapp.ui.statistics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.ui.components.EmptyScreen
import dk.dtu.weatherapp.ui.components.LoadingScreen
import dk.dtu.weatherapp.ui.components.RequestErrorScreen
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.Line

@Composable
fun YearStats(statsViewModel: StatsViewModel) {
    when (val statsYearUIModel = statsViewModel.yearUIState.collectAsState().value) {
        StatsYearUIModel.RequestError -> RequestErrorScreen()
        StatsYearUIModel.Empty -> EmptyScreen("No available data from the server")
        StatsYearUIModel.Loading -> LoadingScreen()
        is StatsYearUIModel.Data -> {
            LineChart(
                data = remember {
                    listOf(
                        Line(
                            label = "Average max",
                            values = statsYearUIModel.months.map { it.temp.averageMax },
                            color = SolidColor(Color.Red),
                            curvedEdges = false,
                        ),
                        Line(
                            label = "Average",
                            values = statsYearUIModel.months.map { it.temp.mean },
                            color = SolidColor(Color.Magenta),
                            curvedEdges = false,
                        ),
                        Line(
                            label = "Average min",
                            values = statsYearUIModel.months.map { it.temp.averageMin },
                            color = SolidColor(Color.Blue),
                            curvedEdges = false,
                            dotProperties = DotProperties(
                                enabled = true,
                                color = SolidColor(Color.White),
                                strokeWidth = 2.dp,
                                radius = 3.dp,
                                strokeColor = SolidColor(Color.Blue),
                            )
                        )
                    )
                }
            )
        }
    }
}