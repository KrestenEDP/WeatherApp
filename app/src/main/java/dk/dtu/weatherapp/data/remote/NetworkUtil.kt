package dk.dtu.weatherapp.data.remote

import android.content.Context

fun saveLastKnownLocation(context: Context, lat: String, lon: String) {
    val sharedPreferences = context.getSharedPreferences("location_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("last_lat", lat)
    editor.putString("last_lon", lon)
    editor.apply()
}

fun getLastKnownLocation(context: Context): Pair<String, String>? {
    val sharedPreferences = context.getSharedPreferences("location_prefs", Context.MODE_PRIVATE)
    val lat = sharedPreferences.getString("last_lat", null)
    val lon = sharedPreferences.getString("last_lon", null)

    return if (lat != null && lon != null) {
        Pair(lat, lon)
    } else {
        null
    }
}
