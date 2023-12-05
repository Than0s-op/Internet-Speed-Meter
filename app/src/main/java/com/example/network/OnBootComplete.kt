package com.example.network

import android.app.Application
import android.content.Intent

class OnBootComplete : Application() {
    override fun onCreate() {
        super.onCreate()
//        startForegroundService(Intent(this, BackgroundService::class.java))
    }
}