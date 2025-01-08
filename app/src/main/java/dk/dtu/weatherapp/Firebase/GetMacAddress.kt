package dk.dtu.weatherapp.Firebase

import android.content.Context
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import androidx.compose.runtime.Composable

@Composable
fun GetMacAddress(context: Context): String? {
    val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val wifiInfo: WifiInfo = wifiManager.connectionInfo
    return wifiInfo.macAddress


}

