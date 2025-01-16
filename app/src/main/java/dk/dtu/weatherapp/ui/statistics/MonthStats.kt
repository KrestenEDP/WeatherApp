package dk.dtu.weatherapp.ui.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dk.dtu.weatherapp.ui.components.CircularList

@Composable
fun MonthStats() {
    var month by remember { mutableIntStateOf(1) }
    MonthPicker(onMonthChange = { month = it })
}

@Composable
fun MonthPicker(onMonthChange: (Int) -> Unit) {
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    ).toMutableList()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularList(
            items = months,
            itemDisplayCount = 3,
            width = 140,
            onItemSelected = {
                onMonthChange(
                    when (it) {
                        "January" -> 1
                        "February" -> 2
                        "March" -> 3
                        "April" -> 4
                        "May" -> 5
                        "June" -> 6
                        "July" -> 7
                        "August" -> 8
                        "September" -> 9
                        "October" -> 10
                        "November" -> 11
                        "December" -> 12
                        else -> 1
                    }
                )
            }
        )
    }
}
