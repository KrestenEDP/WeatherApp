package dk.dtu.weatherapp.domain

import dk.dtu.weatherapp.data.model.DailyWeatherDao
import dk.dtu.weatherapp.data.remote.RemoteWeatherDataSource
import dk.dtu.weatherapp.models.Day
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DayRepository {
    private val dayDataSource = RemoteWeatherDataSource()

    private val mutableDayFlow = MutableSharedFlow<List<Day>>()
    val dayFlow = mutableDayFlow.asSharedFlow()

    suspend fun getDailyForecast() = mutableDayFlow.emit(
        dayDataSource.getDailyWeather().mapToDays()
    )
}

suspend fun DailyWeatherDao.mapToDays(): List<Day> {
    return days.map {
        Day(
            iconURL = "i" + it.weather[0].icon,
            date = getDateFromUnixTimestamp(it.dt),
            dayTemp = convertTempUnit(it.temp.day),
            nightTemp = convertTempUnit(it.temp.night),
            rain = convertPrecipitationUnit(it.rain ?: 0.0)
        )
    }
}

fun getDateFromUnixTimestamp(unixTimestamp: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = unixTimestamp * 1000
    val formatter = SimpleDateFormat("EEEE d. MMMM", Locale.ENGLISH)
    val formattedDate = formatter.format(calendar.time)
    return formattedDate
}