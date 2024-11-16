package dk.dtu.weatherapp.ui.forecast

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
import dk.dtu.weatherapp.ui.theme.Typography
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleDayForecastScreen(
    singleDayIndex: Int? = 0,

) {
    val singleDayViewModel: SingleDayViewModel = viewModel()
    val singleDayUIModel = singleDayViewModel.singleDayUIState.collectAsState().value
    var data: FourDayHourlyUIModel.Data? = null
    when (singleDayUIModel) {
        FourDayHourlyUIModel.Empty -> Text("No data")
        FourDayHourlyUIModel.Loading -> Text("Loading")
        is FourDayHourlyUIModel.Data -> { data = FourDayHourlyUIModel.Data(singleDayUIModel.fourDayHourly) }
    }
    Box {
        val pagerState = rememberPagerState(
            pageCount = { 10 },
            initialPage = singleDayIndex ?: 0
        )

        val indicatorState = rememberPagerState(pageCount = {
            pagerState.pageCount
        })

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                Log.d("Page change", "Page changed to $page")
            }
        }

        HorizontalPager(state = pagerState) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = "January " + (it + 16),
                    style = Typography.titleLarge,
                    modifier = Modifier.padding(8.dp),
                )
                HourlyForecast()
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
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 2f)/2
                            )
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(Color.DarkGray)
                            .size(16.dp)
                    )
                }
            }
            /*repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                if (abs(pagerState.currentPage - iteration) < 2) {
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(16.dp)
                    )
                }
            }*/
        }

        LaunchedEffect(Unit) {
            snapshotFlow {
                Pair(
                    pagerState.currentPage,
                    pagerState.currentPageOffsetFraction
                )
            }.collect { (page, offset) ->
                indicatorState.scrollToPage(page, offset)
            }
        }

    }
}

@Composable
fun HourlyForecast() {
    LazyColumn {
         items(24) { index ->
            ForecastElement(index)
        }
    }
}

@Composable
fun ForecastElement(index: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (index < 10) "0$index:00" else "$index:00",
            style = Typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(PaddingValues(8.dp))
                .weight(1.5f)
        )
        Icon(
            imageVector = Icons.Filled.Face,
            contentDescription = null, // TODO: Add weather type as content description
            modifier = Modifier
                .padding(PaddingValues(start = 16.dp, end = 8.dp))
                .weight(1f)
        )
        Text(
            text = "${index*(index%3-1)}°C",
            style = Typography.bodyLarge,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(PaddingValues(8.dp))
                .weight(1.5f)
        )
        Text(
            text = "$index,0 mm",
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
                imageVector = Icons.Filled.Menu,
                contentDescription = null, // TODO: Add more options as content description
                modifier = Modifier.padding(start=8.dp, end=0.dp)
            )
            Text(
                text = "23 m/s",
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
    Scaffold { innerPadding ->
        SingleDayForecastScreen()
    }
}