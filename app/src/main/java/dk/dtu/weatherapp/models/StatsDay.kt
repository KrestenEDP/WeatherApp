package dk.dtu.weatherapp.models

data class StatsDay (
    val month: Int,
    val temp: StatsDataObject,
    val pressure: StatsDataObject,
    val humidity: StatsDataObject,
    val wind: StatsDataObject,
    val precipitation: StatsDataObject,
    val clouds: StatsDataObject
)

data class StatsDataObject (
    val mean: Double,
    val min: Double,
    val max: Double,
)