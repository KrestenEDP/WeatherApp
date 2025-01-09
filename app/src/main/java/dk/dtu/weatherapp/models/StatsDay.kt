package dk.dtu.weatherapp.models

data class StatsDay (
    val month: Int,
    val day: Int,
    val temp: List<Double>,
    val pressure: List<Double>,
    val humidity: List<Double>,
    val wind: List<Double>,
    val precipitation: List<Double>,
    val clouds: List<Double>
)