package com.example.network

import android.net.TrafficStats

class Speed {

	private var lastTotalRxBytes: Long = 0
	private var lastTotalTxBytes: Long = 0
	private var lastUpdateTime: Long = System.currentTimeMillis()

	fun calculateSpeed(isWifi:Boolean):Array<Long> {

		val currentRxBytes = TrafficStats.getTotalRxBytes()
		val currentTxBytes = TrafficStats.getTotalTxBytes()
		val currentTime = System.currentTimeMillis()
		var downloadSpeed:Long = 0
		var uploadSpeed:Long = 0

		if (lastUpdateTime != 0L && lastTotalRxBytes != 0L && lastTotalTxBytes != 0L) {

			downloadSpeed =
				(currentRxBytes - lastTotalRxBytes) / (currentTime - lastUpdateTime) * 1000 // Bytes per second
			uploadSpeed =
				(currentTxBytes - lastTotalTxBytes) / (currentTime - lastUpdateTime) * 1000 // Bytes per second

			if (isWifi) Global.totalWifiUsedByte += downloadSpeed + uploadSpeed
			else Global.totalMobileUsedByte += downloadSpeed + uploadSpeed
		}

		lastTotalRxBytes = currentRxBytes
		lastTotalTxBytes = currentTxBytes
		lastUpdateTime = currentTime

		return arrayOf(downloadSpeed,uploadSpeed)
	}

//	1024 = 1kb, 1024 * 1000 = 1mb, 1024 * 1000 * 1000 = 1gb  ....
	fun formatSpeed(speed: Long): String {
		return when {
			speed < 1024 * 1000 -> "${speed / 1024} KB/s"
			speed < 1024 * 1000 * 10 -> "${String.format("%.1f", speed / (1024.0 * 1000.0))} MB/s"
			else -> "${speed / (1024 * 1000)} MB/s"
		}
	}

	fun formatDataUsage(used: Long): String {
		return when {
			used < 1024 * 1000000 -> "${String.format("%.1f", used / 1024000.0)} MB"
			else -> "${String.format("%.1f", used / 1024000000.0)} GB"
		}
	}
}