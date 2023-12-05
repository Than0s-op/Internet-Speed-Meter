package com.example.network

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Permission().requestPostNotification(this)
        Permission().requestOffBatteryOptimization(this)


//		val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
//		val intent = Intent(this,EndOfDayReceiver::class.java)
//		val pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
//			PendingIntent.FLAG_IMMUTABLE)
//
//		// Set the alarm to trigger at the end of the day
//		val calendar: Calendar = Calendar.getInstance()
//		calendar.timeInMillis = System.currentTimeMillis()
//		calendar.set(Calendar.HOUR_OF_DAY, 12) // Set the hour to the end of the day
//		calendar.set(Calendar.MINUTE, 3)
//		calendar.set(Calendar.SECOND, 0)
//
//		alarmManager!!.setRepeating(
//			AlarmManager.RTC_WAKEUP,
//			calendar.timeInMillis,
//			AlarmManager.INTERVAL_DAY,
//			pendingIntent
//		)
        val readWrite = ReadWriteLogic(this)
        if(readWrite.read("Counter").first() == "-") readWrite.write("Counter",setOf(Global.totalNumberOfDays.toString()))

        AndroidAlarmScheduler(this).schedule(
            Global.GoodMorning, "DayFinishAlarm"
        )

        AndroidAlarmScheduler(this).schedule(
            LocalDateTime.now().plusSeconds(Global.saveDataTime.toLong()), "SaveDataAlarm"
        )

//		println("Heeeeeeellooooooo woorlllldd")

//		if(!isServiceRunning()) {
        startForegroundService(Intent(this, BackgroundService::class.java))
//		}
    }

//	private fun isServiceRunning(): Boolean {
//		val manager = (ACTIVITY_SERVICE) as ActivityManager
//		for (service in manager.getRunningServices(Int.MAX_VALUE)) {
//			if (BackgroundService::class.java.name == service.service.className) {
//				return true
//			}
//		}
//		return false
//	}
}