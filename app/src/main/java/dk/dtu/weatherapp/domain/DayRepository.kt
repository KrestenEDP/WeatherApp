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

    private val mutableDayFlow = MutableSharedFlow<List<Day>?>()
    val dayFlow = mutableDayFlow.asSharedFlow()

    suspend fun getDailyForecast() = mutableDayFlow.emit(
        dayDataSource.getDailyWeather()?.mapToDays()
    )
}

suspend fun DailyWeatherDao.mapToDays(): List<Day> {
    return days.map {
        Day(
            iconURL = "i" + it.weather[0].icon,
            date = getDateFromUnixTimestamp(it.dt),
            dayTemp = convertTempUnit(it.temp.day),
            nightTemp = convertTempUnit(it.temp.night),
            precipitation = convertPrecipitationUnit((it.rain ?: 0.0) + (it.snow ?: 0.0)),
            windSpeed = convertSpeedUnit(it.speed),
            windGusts = convertSpeedUnit(it.gust),
            windDirection = it.deg,
            cloudiness = it.clouds,
            pressure = it.pressure,
            humidity = it.humidity,
            sunrise = getTimeFromUnixTimestamp(it.sunrise),
            sunset = getTimeFromUnixTimestamp(it.sunset)
        )
    }
}

fun getDateFromUnixTimestamp(unixTimestamp: Long): String {
    return formatTime(unixTimestamp, "EEEE d. MMMM")
}

fun getTimeFromUnixTimestamp(unixTimestamp: Long): String {
    return formatTime(unixTimestamp, "HH:mm")
}

private fun formatTime(unixTimestamp: Long, format: String): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = unixTimestamp * 1000
    val formatter = SimpleDateFormat(format, Locale.ENGLISH)
    val formattedTime = formatter.format(calendar.time)
    return formattedTime
}