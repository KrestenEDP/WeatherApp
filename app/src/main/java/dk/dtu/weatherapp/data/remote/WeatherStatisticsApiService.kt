package dk.dtu.weatherapp.data.remote

import dk.dtu.weatherapp.data.model.StatsDataDao
import dk.dtu.weatherapp.data.model.StatsListDao
import dk.dtu.weatherapp.data.model.StatsYearDao
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherStatisticsApiService {
    @GET("/data/2.5/aggregated/day?appid=$WeatherApiKey")
    suspend fun getDayStatistics(
        @Query("lat") lat: Double = 55.77,
        @Query("lon") lon: Double = 12.50,
        @Query("month") month: Int = 2,
        @Query("day") day: Int = 2,
    ): StatsDataDao

    @GET("/data/2.5/aggregated/month?appid=$WeatherApiKey")
    suspend fun getMonthStatistics(
        @Query("lat") lat: Double = 55.77,
        @Query("lon") lon: Double = 12.50,
        @Query("month") month: Int = 2,
    ): StatsDataDao

    @GET("/data/2.5/aggregated/year?&appid=$WeatherApiKey")
    suspend fun getYearStatistics(
        @Query("lat") lat: Double = 55.77,
        @Query("lon") lon: Double = 12.50,
    ): StatsYearDao
}