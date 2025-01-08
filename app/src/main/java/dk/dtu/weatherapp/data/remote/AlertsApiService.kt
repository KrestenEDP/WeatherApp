package dk.dtu.weatherapp.data.remote
import dk.dtu.weatherapp.data.model.AlertsParentDao
import retrofit2.http.GET
import retrofit2.http.Query

interface AlertsApiService {
    @GET("/v1/alerts.json?key=$AlertsApiKey")
    suspend fun getAlerts(
        @Query("q") q: String = "55.77,12.5",
        @Query("units") units: String = "metric"
    ): AlertsParentDao
}
