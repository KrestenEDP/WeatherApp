package dk.dtu.weatherapp.data.remote

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class RemoteWeatherDataSource {
    companion object {
        const val BASE_URL = "https://api.openweathermap.org"
        private const val CONTENT_TYPE = "application/json; charset=UTF8"
    }

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory(CONTENT_TYPE.toMediaType())

        )
        .baseUrl(BASE_URL)
        .build()

    private val weatherApi: WeatherApiService = retrofit.create(WeatherApiService::class.java)

    suspend fun getCurrentWeather() = weatherApi.getCurrentWeather()
    suspend fun getHourlyWeatherToday() = weatherApi.getHourlyWeather(12)
    suspend fun getHourlyWeather() = weatherApi.getHourlyWeather()
    suspend fun getDailyWeather() = weatherApi.getDailyWeather()
}