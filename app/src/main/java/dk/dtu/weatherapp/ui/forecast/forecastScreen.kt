package dk.dtu.weatherapp.ui.forecast

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TempScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors( // Change color layout
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "January 16", // TODO make into resource
                        textAlign = TextAlign.Start,
                        style = Typography.titleLarge,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something with navController */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        ForecastScreen(innerPadding)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ForecastScreen(innerPadding: PaddingValues = PaddingValues(16.dp)) {
    Box {
        val pagerState = rememberPagerState(pageCount = {
            10
        })

        HorizontalPager(state = pagerState) {
            Column(
                modifier = Modifier.padding(innerPadding),
            ) {
                HourlyForecast()
            }
        }
        Row(
            Modifier
                .align(Alignment.BottomCenter)
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(16.dp)
                )
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
            text = "$index:00",
            style = Typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(PaddingValues(8.dp))
                .weight(1.5f)
        )
        Icon(
            imageVector = Icons.Filled.Face,
            contentDescription = null, // TODO: Add weather type as content description
            modifier = Modifier
                .padding(PaddingValues(start=16.dp, end=8.dp))
                .weight(1f)
        )
        Text(
            text = "20°C",
            style = Typography.bodyLarge,
            maxLines = 1,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(PaddingValues(8.dp))
                .weight(1.5f)
        )
        Text(
            text = "0,0 mm",
            style = Typography.bodyLarge,
            maxLines = 1,
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
                maxLines = 1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun ForecastScreenPreview() {
    TempScreen()
}