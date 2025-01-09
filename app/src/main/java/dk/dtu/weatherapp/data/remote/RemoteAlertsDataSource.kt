package dk.dtu.weatherapp.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import dk.dtu.weatherapp.getAppContext
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class RemoteAlertsDataSource {
    companion object {
        const val BASE_URL = "https://api.weatherapi.com"
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

    private val alertsApi: AlertsApiService = retrofit.create(AlertsApiService::class.java)

    suspend fun getAlerts() = alertsApi.getAlerts()
}