package com.example.network

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import java.lang.Long.max


class BackgroundService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        println("On task Removed")
    }
    private fun getSpeedFormat(substring: String): String {
        val temp = StringBuilder("000$substring")
        if (temp[temp.length - 2] == '.') temp.setCharAt(temp.length - 2, '_')
        return temp.substring(temp.length - 3, temp.length).toString()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        val activityManager: ActivityManager = this.getSystemService(ACTIVITY_SERVICE) as ActivityManager
//
//        if(activityManager.runningAppProcesses.isEmpty()){
//            return START_NOT_STICKY
//        }
        println("Foreground service activated")

        val speedObj = Speed()
        val notification = NotificationLogic(this).createNotification()
        val connManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var prevSpeed: Long = -1

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                val isWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    ?.getState() == NetworkInfo.State.CONNECTED
                val speed: Array<Long> =
                    speedObj.calculateSpeed(isWifi) // {downloadSpeed , uploadSpeed}

                val format: String = speedObj.formatSpeed(max(speed[0], speed[1]))
                notification.setContentTitle(
                    "Speed: $format"
                )
                notification.setContentText(
                    "Mobile: " + speedObj.formatDataUsage(Global.totalMobileUsedByte) + "   " + "WiFi: " + speedObj.formatDataUsage(
                        Global.totalWifiUsedByte
                    )
                )

                val iconName = "w" + format.substring(format.length - 4, format.length - 2)
                    .lowercase() + getSpeedFormat(format.substring(0, format.length - 5))

                val id = resources.getIdentifier(
                    iconName,
                    "drawable",
                    packageName
                ) // not efficient

                notification.setSmallIcon(id)

                if (prevSpeed != max(speed[0], speed[1])) {
                    startForeground(1, notification.build())
                    prevSpeed = max(speed[0], speed[1])
                }
                handler.postDelayed(this, 1000)
            }
        }, 1000)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Foreground destroyed")
        val readWrite = ReadWriteLogic(this)
        val counter:Int = readWrite.read("Counter").first().toIntOrNull()?:Global.totalNumberOfDays  // set counter value. if is null then set to it total number of days
        readWrite.write(counter.toString(),setOf(Global.totalMobileUsedByte.toString(),Global.totalWifiUsedByte.toString()))
    }
}