package dk.dtu.weatherapp.ui.statistics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.dtu.weatherapp.domain.StatsRepository
import dk.dtu.weatherapp.models.StatsDay
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StatsViewModel : ViewModel() {
    private val statsRepo = StatsRepository()
    private var currentJob: Job? = null

    suspend fun getDayStats(day: Int, month: Int): StatsDay {
        lateinit var data: StatsDay
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            delay(50)
            data = statsRepo.getDayStats(day = day, month = month)
        }
        currentJob?.join()
        return data
    }
}