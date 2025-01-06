package dk.dtu.weatherapp.data.remote
import dk.dtu.weatherapp.data.model.CurrentWeatherDao
import retrofit2.http.GET

interface WeatherApiService {
    @GET("/data/2.5/weather?lat=55.77&lon=12.50&appid=$ApiKey")
    suspend fun getCurrentWeather(): CurrentWeatherDao
}
