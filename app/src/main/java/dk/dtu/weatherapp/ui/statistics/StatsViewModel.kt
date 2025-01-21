package dk.dtu.weatherapp.ui.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.dtu.weatherapp.domain.StatsRepository
import dk.dtu.weatherapp.models.StatsDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatsViewModel : ViewModel() {
    private val statsRepo = StatsRepository()
    private var currentJob: Job? = null

    private val dayMutable = MutableStateFlow<StatsDayUIModel>(StatsDayUIModel.Loading)
    val dayUIState: StateFlow<StatsDayUIModel> = dayMutable

    private val monthMutable = MutableStateFlow<StatsMonthUIModel>(StatsMonthUIModel.Loading)
    val monthUIState: StateFlow<StatsMonthUIModel> = monthMutable

    private val yearMutable = MutableStateFlow<StatsYearUIModel>(StatsYearUIModel.Loading)
    val yearUIState: StateFlow<StatsYearUIModel> = yearMutable

    init {
        viewModelScope.launch {
            statsRepo.dayStatsFlow
                .collect { data ->
                    dayMutable.update {
                        if (data == null) StatsDayUIModel.RequestError
                        else StatsDayUIModel.Data(data)
                    }
                }
        }
        viewModelScope.launch {
            statsRepo.monthStatsFlow
                .collect { data ->
                    monthMutable.update {
                        if (data == null) StatsMonthUIModel.RequestError
                        else StatsMonthUIModel.Data(data)
                    }
                }
        }
        viewModelScope.launch {
            statsRepo.yearStatsFlow
                .collect { data ->
                    yearMutable.update {
                        if (data == null) StatsYearUIModel.RequestError
                        else if (data.isEmpty()) StatsYearUIModel.Empty
                        else StatsYearUIModel.Data(data)
                    }
                }
        }
        getDayStats(1, 1)
        getMonthStats(1)
    }

    fun getDayStats(day: Int, month: Int) = viewModelScope.launch {
        dayMutable.value = StatsDayUIModel.Loading
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            delay(50)
            statsRepo.getDayStats(day = day, month = month)
        }
    }
    fun getMonthStats(month: Int) = viewModelScope.launch {
        monthMutable.value = StatsMonthUIModel.Loading
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            delay(50)
            statsRepo.getMonthStats(month = month)
        }
    }
    fun getYearStats() = viewModelScope.launch {
        if (yearMutable.value is StatsYearUIModel.Data) yearMutable.value
        else {
            yearMutable.value = StatsYearUIModel.Loading
            withContext(Dispatchers.IO) {
                statsRepo.getYearStats()
            }
        }
    }
}

sealed class StatsDayUIModel {
    data object RequestError: StatsDayUIModel()
    data object Empty: StatsDayUIModel()
    data object Loading: StatsDayUIModel()
    data class Data(val day: StatsDay) : StatsDayUIModel()
}

sealed class StatsMonthUIModel {
    data object RequestError: StatsMonthUIModel()
    data object Empty: StatsMonthUIModel()
    data object Loading: StatsMonthUIModel()
    data class Data(val month: StatsDay) : StatsMonthUIModel()
}

sealed class StatsYearUIModel {
    data object RequestError: StatsYearUIModel()
    data object Empty: StatsYearUIModel()
    data object Loading: StatsYearUIModel()
    data class Data(val months: List<StatsDay>) : StatsYearUIModel()
}