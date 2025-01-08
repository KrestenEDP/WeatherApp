package dk.dtu.weatherapp.models

data class Alert(
    val headline: String,
    val severity: String,
    val urgency: String,
    val event: String,
    val description: String
)
