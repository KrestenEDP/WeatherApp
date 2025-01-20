package dk.dtu.weatherapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.dtu.weatherapp.ui.theme.Typography

/**
 * A circular list that can be scrolled infinitely in both directions.
 * @param items The items to display in the list.
 * @param itemHeight The height of each item in the wheel.
 * @param itemDisplayCount The number of items to display at once. Should be an odd number.
 */
@Composable
fun CircularList(
    items: MutableList<String>,
    itemHeight: Int,
    itemDisplayCount: Int,
    width: Int,
    modifier: Modifier = Modifier,
    onItemSelected: (String) -> Unit = {},
) {
    val scrollState = rememberLazyListState(0)
    var lastSelectedIndex by remember { mutableIntStateOf(0) }
    items.add(0, "")
    items.add("")
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemIndex }
            .collect { firstVisibleItemIndex ->
                lastSelectedIndex = (firstVisibleItemIndex + itemDisplayCount / 2)
                onItemSelected(items[lastSelectedIndex])
            }
    }

    Box {
        LazyColumn(
            state = scrollState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .height((itemHeight * itemDisplayCount).dp)
                .width(width.dp)
        ) {
            items(items.size) { index ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier
                        .height(itemHeight.dp)
                ) {
                    Text(
                        text = items[index],
                        style = Typography.titleLarge,
                        color =  if(lastSelectedIndex == index) {
                            MaterialTheme.colorScheme.onBackground
                        } else {
                            Color.Gray

                        },
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .height((itemHeight * itemDisplayCount).dp)
                .width(width.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0f to MaterialTheme.colorScheme.background,
                            0.2f to Color.Transparent,
                            0.8f to Color.Transparent,
                            1f to MaterialTheme.colorScheme.background
                        )
                    )
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CircularListPreview() {
    CircularList(
        items = listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December").toMutableList(),
        itemHeight = 60,
        itemDisplayCount = 3,
        width = 100
    )
}