package dk.dtu.weatherapp.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.dtu.weatherapp.domain.StatsRepository
import dk.dtu.weatherapp.models.StatsDay
import kotlinx.coroutines.launch

class StatsViewModel : ViewModel() {
    private val statsRepo = StatsRepository()
    private var dayStats: StatsDay? = null
    init {
        // Initialize with the stats for current day
        viewModelScope.launch {
            // TODO Use current day and location
            dayStats = statsRepo.getDayStats(2, 2)
        }
    }

    fun getDayStats(): StatsDay? {
        return dayStats
    }
}