package dk.dtu.weatherapp.ui.alerts

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dk.dtu.weatherapp.GlobalUnits
import dk.dtu.weatherapp.R
import dk.dtu.weatherapp.models.Alert

const val CHANNEL_ID = "weather_alert_channel"

// Function to create the notification channel
fun createNotificationChannel(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "ALERTS"
        val descriptionText = "Weather alerts channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}


@SuppressLint("MissingPermission")
fun showNotification(context: Context, alert: Alert) {
    if (GlobalUnits.noticeMe) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.warning2)
            .setContentTitle(alert.headline)
            .setContentText(alert.event)
            .setStyle(NotificationCompat.BigTextStyle().bigText(alert.description))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)

        val notification = builder.build()
        notification.flags = notification.flags or Notification.FLAG_ONLY_ALERT_ONCE

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}




