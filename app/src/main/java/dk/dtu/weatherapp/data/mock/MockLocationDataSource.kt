package dk.dtu.weatherapp.data.mock

import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.models.Location

class MockLocationDataSource {
    private val data = listOf (
        Location("Copenhagen", 22.5, R.drawable.i01n, false),
        Location("Ballerup", 15.5, R.drawable.i01n, false)
    )

    fun getLocations(): List<Location> {
        return data
    }
}