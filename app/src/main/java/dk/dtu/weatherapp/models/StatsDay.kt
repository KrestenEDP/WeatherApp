package dk.dtu.weatherapp.models

data class StatsDay (
    val month: Int,
    val day: Int,
    val temp: StatsDayTemp,
    val pressure: StatsDayPressure,
    val humidity: StatsDayHumidity,
    val wind: StatsDayWind,
    val precipitation: StatsDayPrecipitation,
    val clouds: StatsDayClouds
)

data class StatsDayTemp (
    val mean: Double
)

data class StatsDayPressure (
    val mean: Double
)

data class StatsDayHumidity (
    val mean: Double
)

data class StatsDayWind (
    val mean: Double
)

data class StatsDayPrecipitation (
    val mean: Double
)

data class StatsDayClouds (
    val mean: Double
)