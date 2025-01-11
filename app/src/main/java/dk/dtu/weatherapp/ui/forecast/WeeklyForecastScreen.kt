package dk.dtu.weatherapp.ui.forecast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import dk.dtu.weatherapp.GlobalUnits
import dk.dtu.weatherapp.data.mock.MockHourlyFourDayForecast
import dk.dtu.weatherapp.models.Hour
import dk.dtu.weatherapp.navigation.SingleDayForecast
import dk.dtu.weatherapp.ui.components.LoadingScreen
import dk.dtu.weatherapp.ui.theme.Typography
import dk.dtu.weatherapp.ui.util.getPainterResource
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.absoluteValue

@Composable
fun SingleDayForecastScreen(
    singleDayIndex: Int? = 0,
) {
    val weeklyForecastViewModel: WeeklyForecastViewModel = viewModel()


    when (val dayUIModel = weeklyForecastViewModel.dayUIState.collectAsState().value) {
        DayUIModel.Empty -> Text("No data")
        DayUIModel.Loading -> LoadingScreen()
        is DayUIModel.Data -> {
            SingleDayForecastContent(
                weeklyForecastViewModel.hourlyUIState.collectAsState().value,
                dayUIModel,
                singleDayIndex
            )
        }
    }
}

@Composable
fun SingleDayForecastContent(
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                /*Text(
                    text = forecastUiModel.fourDayHourly[it][0].time, // TODO Change to day instead of hour
                    style = Typography.titleLarge,
                    modifier = Modifier.padding(8.dp),
                )*/
                when (forecastHourlyUiModel) {
                    HourlyUIModel.Empty -> Text("No data")
                    HourlyUIModel.Loading -> LoadingScreen()
                    is HourlyUIModel.Data -> {
                        if (it < forecastHourlyUiModel.hours.size) {
                            HourlyForecast(forecastHourlyUiModel.hours[it])
                        }
                    }
                }
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
                pageSize = PageSize.Fixed(20.dp),
                contentPadding = PaddingValues(horizontal = 40.dp),
                userScrollEnabled = false,
                modifier = Modifier
                    .width(100.dp)
                    .align(Alignment.CenterVertically)
                    .padding(bottom=8.dp)

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
                            .size(16.dp)
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
                SingleDayForecast.appBarTitle = formatDate(forecastDailyUiState.days[page].date)
            }
        }
    }
}

@Composable
fun HourlyForecast(forecast: List<Hour>) {
    LazyColumn {
         items(forecast.size) {
            ForecastElement(forecast[it])
        }
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
                .padding(PaddingValues(8.dp))
                .weight(1.5f)
        )

        Icon(
            imageVector = ImageVector.vectorResource(getPainterResource(hour.iconURL, LocalContext.current)),
            contentDescription = null, // TODO: Add weather type as content description
            modifier = Modifier
                .padding(PaddingValues(start = 16.dp, end = 8.dp))
                .weight(1f)
        )
        Text(
            text = "${hour.temp} " + GlobalUnits.temp,
            style = Typography.bodyLarge,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(PaddingValues(8.dp))
                .weight(1.5f)
        )
        Text(
            text = "${hour.precipitation}  " + GlobalUnits.precipitation,
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
                .weight(2.5f)
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
    val inputFormatter = SimpleDateFormat("EEEE d. MMMM", Locale.getDefault())
    val date = inputFormatter.parse(dateString) ?: ""
    val outputFormatter = SimpleDateFormat("MMMM d", Locale.getDefault())
    return outputFormatter.format(date)
}