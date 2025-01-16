package dk.dtu.weatherapp.ui.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import dk.dtu.weatherapp.data.mock.MockHourlyFourDayForecast
import dk.dtu.weatherapp.navigation.WeeklyForecast
import dk.dtu.weatherapp.ui.components.LoadingScreen
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.absoluteValue

@Composable
fun WeeklyForecastScreen(
    singleDayIndex: Int? = 0,
) {
    val weeklyForecastViewModel: WeeklyForecastViewModel = viewModel()

    when (val dayUIModel = weeklyForecastViewModel.dayUIState.collectAsState().value) {
        DayUIModel.Empty -> Text("No data")
        DayUIModel.Loading -> LoadingScreen()
        is DayUIModel.Data -> {
            WeeklyForecastContent(
                weeklyForecastViewModel.hourlyUIState.collectAsState().value,
                dayUIModel,
                singleDayIndex
            )
        }
    }
}

@Composable
fun WeeklyForecastContent(
    forecastHourlyUiModel: HourlyUIModel,
    forecastDailyUiState: DayUIModel.Data,
    singleDayIndex: Int? = 0
) {
    Box {
        val pagerState = rememberPagerState(
            pageCount = { forecastDailyUiState.days.size },
            initialPage = singleDayIndex ?: 0
        )

        val indicatorState = rememberPagerState(pageCount = {
            pagerState.pageCount
        })

        HorizontalPager(state = pagerState) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                /*Text(
                    text = forecastUiModel.fourDayHourly[it][0].time, // TODO Change to day instead of hour
                    style = Typography.titleLarge,
                    modifier = Modifier.padding(8.dp),
                )*/
                var showMoreInformationBoxes = false
                when (forecastHourlyUiModel) {
                    HourlyUIModel.Empty -> item { Text("No data") }
                    HourlyUIModel.Loading -> item { LoadingScreen(modifier = Modifier.padding(vertical = 100.dp)) }
                    is HourlyUIModel.Data -> {
                        if (it < forecastHourlyUiModel.hours.size) {
                            HourlyForecast(forecastHourlyUiModel.hours[it])
                        } else showMoreInformationBoxes = true
                    }
                }
                item { InformationBoxes(forecastDailyUiState.days[it], showMoreInformationBoxes) }
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            HorizontalPager(
                state = indicatorState,
                pageSize = PageSize.Fixed(16.dp),
                contentPadding = PaddingValues(horizontal = 20.dp),
                userScrollEnabled = false,
                modifier = Modifier
                    .width(56.dp)
                    .align(Alignment.CenterVertically)
                    .padding(bottom = 6.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(color = Color.LightGray.copy(alpha = 0.4f))
                    .padding(vertical = 2.dp)

            ) { page ->
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            // Calculate the absolute offset for the current page from the
                            // scroll position. We use the absolute value which allows us to mirror
                            // any effects for both directions
                            val pageOffset = (
                                    (pagerState.currentPage - page) + pagerState
                                        .currentPageOffsetFraction
                                    ).absoluteValue

                            alpha = lerp(
                                start = 0f,
                                stop = 0.8f,
                                fraction = 0.8f - pageOffset.coerceIn(0f, 2f)/2
                            )
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color = Color.Black)
                            .size(12.dp)
                    )
                }
            }
        }

        LaunchedEffect(Unit) {
            snapshotFlow {
                Pair(
                    pagerState.currentPage,
                    pagerState.currentPageOffsetFraction
                )
            }.collect { (page, offset) ->
                indicatorState.scrollToPage(page, offset)
                WeeklyForecast.appBarTitle = formatDate(forecastDailyUiState.days[page].date)
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SingleDayForecastScreenPreview() {
    val data = HourlyUIModel.Data(MockHourlyFourDayForecast().getHourlyWeather().chunked(24))
    //SingleDayForecastContent(data, 0)
}
private fun formatDate(dateString: String): String {
    val inputFormatter = SimpleDateFormat("EEEE d. MMMM", Locale.ENGLISH)
    val date = inputFormatter.parse(dateString) ?: ""
    val outputFormatter = SimpleDateFormat("MMMM d", Locale.ENGLISH)
    return outputFormatter.format(date)
}