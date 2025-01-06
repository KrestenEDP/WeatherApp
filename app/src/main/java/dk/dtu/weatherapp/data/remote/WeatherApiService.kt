package dk.dtu.weatherapp.data.remote
import retrofit2.http.GET

interface WeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getWeather(): String
}
