package dk.dtu.weatherapp.data.remote

import android.content.Context
import android.util.Log
import dk.dtu.weatherapp.data.model.AlertsParentDao
import dk.dtu.weatherapp.domain.getCurrentLocation
import dk.dtu.weatherapp.getAppContext
import dk.dtu.weatherapp.ui.util.hasNetwork
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

    private val alertsApi: AlertsApiService = retrofit.create(AlertsApiService::class.java)

    suspend fun getAlerts(): AlertsParentDao? {
        return try {
            alertsApi.getAlerts(q = "${getCurrentLocation().lat},${getCurrentLocation().lon}")
        } catch (e: Exception) {
            Log.e("RemoteAlertsDataSource", "Failed to get alerts", e)
            null
        }
    }
}