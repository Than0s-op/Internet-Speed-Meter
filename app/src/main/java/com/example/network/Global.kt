package com.example.network

import java.time.LocalDateTime

class Global {
    companion object{
        const val totalNumberOfDays = 40
        const val saveDataTime = 10
        var totalMobileUsedByte: Long = 0
        var totalWifiUsedByte: Long = 0
        val GoodMorning:LocalDateTime = LocalDateTime.now().run {
            plusSeconds(59L - second).run {
                plusMinutes(59L - minute)
            }.run {
                plusHours(23L - hour)
            }
        }
        var isForeGroundRunning:Boolean = false
    }
}