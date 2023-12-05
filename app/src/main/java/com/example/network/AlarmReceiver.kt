package com.example.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.time.LocalDateTime

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        println("Yes ${intent?.action.toString()}")

        if(intent?.action == Intent.ACTION_BOOT_COMPLETED){
            println("Boot Completed")
            val readWrite = ReadWriteLogic(context!!)
            println("Counter: " + readWrite.read("Counter").first())
            val data = readWrite.read(readWrite.read("Counter").first())
            println("data: " + data)

            Global.totalMobileUsedByte = data.first().toLongOrNull()?: 0   // set first value (Mobile data). if it is null then set 0.
            Global.totalWifiUsedByte = data.last().toLongOrNull()?: 0      // set last value (Wifi Data). if it is null then set 0.

//          set Day Finish Alarm
            AndroidAlarmScheduler(context).schedule(LocalDateTime.now().plusSeconds(Global.saveDataTime.toLong()),Global.saveDataTime.toLong(),"SaveDataAlarm")
            AndroidAlarmScheduler(context).schedule(Global.GoodMorning,null,"DayFinishAlarm")

            context?.startForegroundService(Intent(context, BackgroundService::class.java))
        }

        if(intent?.action == "DayFinishAlarm"){
            println("Alarm triggered: Day Finish Alarm")

            val readWrite = ReadWriteLogic(context!!)
            var counter:Int = readWrite.read("Counter").first().toIntOrNull()?:Global.totalNumberOfDays  // set counter value. if is null then set to it total number of days

            readWrite.write(counter.toString(),setOf(Global.totalMobileUsedByte.toString(),Global.totalWifiUsedByte.toString()))
            counter = if(counter-1 < 0) Global.totalNumberOfDays else counter-1
            Global.totalWifiUsedByte = 0
            Global.totalMobileUsedByte = 0
            readWrite.write("Counter",setOf(counter.toString()))
            AndroidAlarmScheduler(context).schedule(Global.GoodMorning,null,"DayFinishAlarm")
        }

        if(intent?.action == "SaveDataAlarm"){
            println("Save Data Alarm triggered")
            val readWrite = ReadWriteLogic(context!!)
            val counter:Int = readWrite.read("Counter").first().toIntOrNull()?:Global.totalNumberOfDays.toInt()  // set counter value. if is null then set to it total number of days
            readWrite.write(counter.toString(),setOf(Global.totalMobileUsedByte.toString(),Global.totalWifiUsedByte.toString()))
//            AndroidAlarmScheduler(context).schedule(LocalDateTime.now().plusSeconds(Global.saveDataTime.toLong()),"SaveDataAlarm")
        }
    }
}