package dk.dtu.weatherapp.data.remote
import dk.dtu.weatherapp.data.model.DailyWeatherDao
import dk.dtu.weatherapp.data.model.HourlyWeatherDao
import dk.dtu.weatherapp.data.model.WeatherHourDao
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("/data/2.5/weather?appid=$ApiKey")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double = 55.77,
        @Query("lon") lon: Double = 12.50
    ): WeatherHourDao

    @GET("/data/2.5/forecast/daily?appid=$ApiKey")
    suspend fun getDailyWeather(
        @Query("lat") lat: Double = 55.77,
        @Query("lon") lon: Double = 12.50
        ): DailyWeatherDao

    @GET("/data/2.5/forecast/hourly?appid=$ApiKey")
    suspend fun getHourlyWeather(
        @Query("lat") lat: Double = 55.77,
        @Query("lon") lon: Double = 12.50,
        @Query("cnt") count: Int = 96
    ): HourlyWeatherDao
}
