package dk.dtu.weatherapp.data.remote
import dk.dtu.weatherapp.data.model.DailyWeatherDao
import dk.dtu.weatherapp.data.model.HourlyWeatherDao
import dk.dtu.weatherapp.data.model.WeatherHourDao
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("/data/2.5/weather?appid=$WeatherApiKey")
    suspend fun getCurrentWeather(
        @Query("lat") lat: String = "55.77",
        @Query("lon") lon: String = "12.50",
        //@Query("units") units: String = "metric"
    ): WeatherHourDao

    @GET("/data/2.5/forecast/daily?cont=16&appid=$WeatherApiKey")
    suspend fun getDailyWeather(
        @Query("lat") lat: String = "55.77",
        @Query("lon") lon: String = "12.50",
        //@Query("units") units: String = "metric"
        ): DailyWeatherDao

    @GET("/data/2.5/forecast/hourly?appid=$WeatherApiKey")
    suspend fun getHourlyWeather(
        @Query("lat") lat: String = "55.77",
        @Query("lon") lon: String = "12.50",
        @Query("cnt") count: Int = 96,
        //@Query("units") units: String = "metric"
    ): HourlyWeatherDao
}
