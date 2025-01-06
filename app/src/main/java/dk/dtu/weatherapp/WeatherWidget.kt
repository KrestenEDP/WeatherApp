package dk.dtu.weatherapp

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent

class WeatherWidget: GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            CurrentWeatherWidget()
        }
    }
}

class WeatherWidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = WeatherWidget()

}

@Composable
fun CurrentWeatherWidget() {
    Button(
        text = "Current Weather",
        onClick = actionStartActivity<WeatherActivity>()
    )
}
