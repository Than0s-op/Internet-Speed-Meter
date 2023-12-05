package com.example.network

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationLogic(private val context: Context) {
	val CHANNEL_ID = "Show lockscreen notification"

	fun createNotification(): NotificationCompat.Builder {
        Log.d("AXZ","create notification logic")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel.
            val name = "Show lockscreen notification"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)

            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            val notificationManager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            mChannel.setShowBadge(false)
            notificationManager.createNotificationChannel(mChannel)
        }

		val intent = Intent(context, MainActivity::class.java)
		val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false) // Auto-dismiss the notification when the user taps on it
            .setOngoing(true)
			.setContentIntent(pendingIntent)
            .setShowWhen(false)
            .setSilent(true)
    }

//    fun pushNotification(notification: NotificationCompat.Builder) {
//		with(NotificationManagerCompat.from(context)) {
//			// notificationId is a unique int for each notification that you must define.
//			if (ActivityCompat.checkSelfPermission(
//					context,
//					Manifest.permission.POST_NOTIFICATIONS
//				) == PackageManager.PERMISSION_GRANTED
//			) {
//                notify(101, notification.build())
//            }
//		}
//    }
}