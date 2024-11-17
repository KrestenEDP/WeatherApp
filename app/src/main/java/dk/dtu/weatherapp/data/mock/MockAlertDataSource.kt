package dk.dtu.weatherapp.data.mock

import dk.dtu.weatherapp.models.Alert

class MockAlertDataSource {
    private val data = listOf (
    Alert("Thunderstorm", "Thunderstorm is expected in your area", "10/10", "12/10", listOf("thunderstorm")),
)

    fun getAlerts(): List<Alert> {
        return data
    }
}