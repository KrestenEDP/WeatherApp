package dk.dtu.weatherapp.ui.util

import android.content.Context


fun getPainterResource(id: String, context: Context): Int {
    val icon = context.resources.getIdentifier(id, "drawable", context.packageName)
    return icon
}