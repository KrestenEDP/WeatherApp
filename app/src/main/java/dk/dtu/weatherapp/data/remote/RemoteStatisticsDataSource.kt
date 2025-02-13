package dk.dtu.weatherapp.data.remote

import android.content.Context
import android.util.Log
import dk.dtu.weatherapp.data.model.StatsDataDao
import dk.dtu.weatherapp.domain.getCurrentLocation
import dk.dtu.weatherapp.getAppContext
import dk.dtu.weatherapp.ui.util.hasNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class RemoteStatisticsDataSource {
    companion object {
        const val BASE_URL = "https://history.openweathermap.org"
        private const val CONTENT_TYPE = "application/json; charset=UTF8"
    }

    private val json = Json {
        ignoreUnknownKeys = true
    }

    val context: Context = getAppContext()
    private val cacheSize = (1024 * 1024).toLong() // 1 MB
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
            
            chain.proceed(request)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory(CONTENT_TYPE.toMediaType())
        )
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()

    private val statisticsApi: WeatherStatisticsApiService = retrofit.create(WeatherStatisticsApiService::class.java)

    suspend fun getDayStatistics(month: Int, day: Int): StatsDataDao? {
        return try {
            statisticsApi.getDayStatistics(getCurrentLocation().lat, getCurrentLocation().lon, month, day)
        } catch (e: Exception) {
            Log.e("RemoteWeatherDataSource", "Failed to get current weather", e)
            null
        }
    }

    suspend fun getMonthStatistics(month: Int): StatsDataDao? {
        return try {
            statisticsApi.getMonthStatistics(getCurrentLocation().lat, getCurrentLocation().lon, month)
        } catch (e: Exception) {
            Log.e("RemoteWeatherDataSource", "Failed to get hourly weather for today", e)
            null
        }
    }

    suspend fun getYearStatistics(): List<StatsDataDao>? {
        val coroutineScope = CoroutineScope(Dispatchers.IO)

        return try {
            val deferredList: List<Deferred<StatsDataDao?>> = (1..12).map { month ->
                coroutineScope.async {
                    getMonthStatistics(month)
                }
            }
            val results: List<StatsDataDao?> = deferredList.awaitAll()

            if (results.any { it == null }) {
                null
            } else {
                results.filterNotNull()
            }
        } catch (e: Exception) {
            Log.e("RemoteWeatherDataSource", "Failed to get yearly weather", e)
            null
        }
    }
}