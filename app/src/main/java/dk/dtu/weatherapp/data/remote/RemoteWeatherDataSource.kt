package dk.dtu.weatherapp.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.datastore.preferences.core.intPreferencesKey
import dk.dtu.weatherapp.domain.dataStore
import dk.dtu.weatherapp.getAppContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
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

            request = if (hasNetwork(context)!!)
                request.newBuilder().header("Cache-Control", "public, max-age=" + 1800 + ", stale-while-revalidate=" + 300).build()
            else
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()

            val response = chain.proceed(request)

            // Log whether the response is coming from the cache or network
            if (response.cacheResponse != null) {
                Log.d("RemoteTest","Serving from Cache")
            } else {
                Log.d("RemoteTest","Requesting from Network")
            }

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

    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    private val weatherApi: WeatherApiService = retrofit.create(WeatherApiService::class.java)

    suspend fun getCurrentWeather() = weatherApi.getCurrentWeather(
        units = when (getUnitSetting(context)) {
            0 -> "metric"
            1 -> "imperial"
            else -> "standard"
        })
    suspend fun getHourlyWeatherToday(count: Int) = weatherApi.getHourlyWeather(
        count = count,
        units = when (getUnitSetting(context)) {
            0 -> "metric"
            1 -> "imperial"
            else -> "standard"
        })
    suspend fun getHourlyWeather() = weatherApi.getHourlyWeather(
        units = when (getUnitSetting(context)) {
            0 -> "metric"
            1 -> "imperial"
            else -> "standard"
        })
    suspend fun getDailyWeather() = weatherApi.getDailyWeather(
        units = when (getUnitSetting(context)) {
            0 -> "metric"
            1 -> "imperial"
            else -> "standard"
        })

    suspend fun getUnitSetting(context: Context): Int {
        val dataStore = context.dataStore.data
        val preferredUnitKey = intPreferencesKey("preferred_unit")


        return dataStore.map { preferences ->
            preferences[preferredUnitKey] ?: 0
        }.first()
    }
}