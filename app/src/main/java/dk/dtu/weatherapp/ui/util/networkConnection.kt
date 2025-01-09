package dk.dtu.weatherapp.ui.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


/**
* Check if the device has network connection
* @return true if device is connected to network, false otherwise
 */
fun hasNetwork(context: Context): Boolean? {
    var isConnected: Boolean? = false // Initial Value
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}