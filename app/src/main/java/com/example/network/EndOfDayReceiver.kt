package com.example.network;

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService


class EndOfDayReceiver(private val context:Context) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Perform your desired operation when the day ends
        // This method will be called when the alarm is triggered
        // Add your logic here
        Log.d("AXZ","HEllllllll")
    }
//    fun Fun(){
//
//    }
}
