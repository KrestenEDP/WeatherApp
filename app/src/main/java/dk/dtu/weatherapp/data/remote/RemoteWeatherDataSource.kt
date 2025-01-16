package dk.dtu.weatherapp.data.remote

import android.content.Context
import android.util.Log
import dk.dtu.weatherapp.data.model.DailyWeatherDao
import dk.dtu.weatherapp.data.model.HourlyWeatherDao
import dk.dtu.weatherapp.data.model.WeatherHourDao
import dk.dtu.weatherapp.domain.getCurrentLocation
import dk.dtu.weatherapp.getAppContext
import dk.dtu.weatherapp.ui.util.hasNetwork
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
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

    val context: Context = getAppContext()

    private val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
    private val cache = Cache(context.cacheDir, cacheSize)
    private val okHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor { chain ->
            var request = chain.request()

            val currentLocation = getCurrentLocation()
            val lastKnownLocation = getLastKnownLocation(context)

            val locationChanged = lastKnownLocation == null ||
                    lastKnownLocation.first != currentLocation.lat ||
                    lastKnownLocation.second != currentLocation.lon

            if (locationChanged) {
                request = request.newBuilder()
                    .header("Cache-Control", "no-cache, no-store, must-revalidate")
                    .build()

                saveLastKnownLocation(context, currentLocation.lat, currentLocation.lon)
            } else {
                request = if (hasNetwork(context)!!)
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, max-age=" + 1800 + ", stale-while-revalidate=" + 300
                    ).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
            }

            val response = chain.proceed(request)

            return@addInterceptor response
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory(CONTENT_TYPE.toMediaType())
        )
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()

    private val weatherApi: WeatherApiService = retrofit.create(WeatherApiService::class.java)

    suspend fun getCurrentWeather(): WeatherHourDao? {
        return try {
            weatherApi.getCurrentWeather(lat = getCurrentLocation().lat, lon = getCurrentLocation().lon)
        } catch (e: Exception) {
            Log.e("RemoteWeatherDataSource", "Failed to get current weather", e)
            null
        }
    }

    suspend fun getHourlyWeatherToday(count: Int): HourlyWeatherDao? {
        return try {
            weatherApi.getHourlyWeather(count = count, lat = getCurrentLocation().lat, lon = getCurrentLocation().lon)
        } catch (e: Exception) {
            Log.e("RemoteWeatherDataSource", "Failed to get hourly weather for today", e)
            null
        }
    }

    suspend fun getHourlyWeather(): HourlyWeatherDao? {
        return try {
            weatherApi.getHourlyWeather(lat = getCurrentLocation().lat, lon = getCurrentLocation().lon)
        } catch (e: Exception) {
            Log.e("RemoteWeatherDataSource", "Failed to get hourly weather", e)
            null
        }
    }

    suspend fun getDailyWeather(): DailyWeatherDao? {
        return try {
            weatherApi.getDailyWeather(lat = getCurrentLocation().lat, lon = getCurrentLocation().lon)
        } catch (e: Exception) {
            Log.e("RemoteWeatherDataSource", "Failed to get daily weather", e)
            null
        }
    }
}