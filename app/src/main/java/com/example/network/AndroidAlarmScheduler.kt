package com.example.network

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.time.LocalDateTime
import java.time.ZoneId

class AndroidAlarmScheduler(
    private val context: Context
) {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    fun schedule(item: LocalDateTime,action:String) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            this.action = action
        }

        // below code view in detail
        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            item.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )

    }
//
//    override fun cancel(item: AlarmItem) {
//        alarmManager.cancel(
//            PendingIntent.getBroadcast(
//                context,
//                item.hashCode(),
//                Intent(context, AlarmReceiver::class.java),
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
//        )
//    }
}