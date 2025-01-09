package dk.dtu.weatherapp.ui.statistics

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dk.dtu.weatherapp.domain.StatsRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticScreen() {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    var selectedDate by remember { mutableLongStateOf(System.currentTimeMillis()) }
    val statsRepo = StatsRepository()
    val datePickerState = rememberDatePickerState(
        yearRange = currentYear - 46..currentYear,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        DatePicker(
            state = datePickerState
        )
    }

    selectedDate = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
}