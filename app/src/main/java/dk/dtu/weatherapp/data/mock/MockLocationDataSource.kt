package dk.dtu.weatherapp.data.mock

import dk.dtu.weatherapp.models.Location

class MockLocationDataSource {
    private val data = listOf (
        Location("Copenhagen", "55.6761", "12.5683", false),
        Location("Ballerup", "55.7317", "12.3633", false),
    )

    fun getLocations(): List<Location> {
        return data
    }
}