package dk.dtu.weatherapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AlertDao(
    val headline: String,
    val severity: String,
    val urgency: String,
    val event: String,
    val desc: String
)
@Serializable
data class AlertsDao(
    @SerialName("alert")
    val alerts: List<AlertDao>
)

@Serializable
data class AlertsParentDao(
    @SerialName("alerts")
    val alertList: AlertsDao
)
