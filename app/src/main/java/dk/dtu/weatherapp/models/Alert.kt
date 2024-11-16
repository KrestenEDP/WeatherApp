package dk.dtu.weatherapp.models

data class Alert(
    val event: String,
    val description: String,
    val startTime: String, // TODO: Change to Date
    val endTime: String, // TODO: Change to Date
    val tags: List<String>
)
