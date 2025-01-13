package dk.dtu.weatherapp.ui.alerts

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.models.Alert

// Define the notification channel ID as a constant
const val CHANNEL_ID = "weather_alert_channel"

// Function to create the notification channel
fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "ALERTS"
        val descriptionText = "Weather alerts channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

// Function to show a notification
@SuppressLint("MissingPermission")
fun showNotification(context: Context, alert: Alert) {
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.warning)
        .setContentTitle(alert.headline)
        .setContentText(alert.event)
        .setStyle(NotificationCompat.BigTextStyle().bigText(alert.description))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    val notificationManager = NotificationManagerCompat.from(context)
    notificationManager.notify(0, builder.build()) // 0 is the notification ID, change if needed
}
