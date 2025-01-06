package dk.dtu.weatherapp.data.remote
import dk.dtu.weatherapp.data.model.DailyWeatherDao
import dk.dtu.weatherapp.data.model.HourlyWeatherDao
import dk.dtu.weatherapp.data.model.WeatherHourDao
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("/data/2.5/weather?lat=55.77&lon=12.50&appid=$ApiKey")
    suspend fun getCurrentWeather(): WeatherHourDao

    @GET("/data/2.5/forecast/daily?lat=55.77&lon=12.50&cnt=4&appid=$ApiKey")
    suspend fun getDailyWeather(): DailyWeatherDao

    @GET("/data/2.5/forecast/hourly?lat=55.77&lon=12.50&appid=$ApiKey")
    suspend fun getHourlyWeather(
        @Query("cnt") count: Int = 96
    ): HourlyWeatherDao
}
