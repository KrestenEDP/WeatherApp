package dk.dtu.weatherapp.ui.util

import android.content.Context
import dk.dtu.weatherapp.R


fun getPainterResource(id: String, context: Context): Int {
    val icon = context.resources.getIdentifier(id, "drawable", context.packageName)
    if (icon == 0) {
        return R.drawable.ic_launcher_foreground
    }
    return icon
}