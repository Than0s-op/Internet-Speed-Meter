package com.example.network

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class Permission {
	fun requestOffBatteryOptimization(context: Context) {
		val intent = Intent()
		val packageName = context.packageName
		val pm = context.getSystemService(AppCompatActivity.POWER_SERVICE) as PowerManager
		if (!pm.isIgnoringBatteryOptimizations(packageName)) {
			intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
			intent.data = Uri.parse("package:$packageName")
			context.startActivity(intent)
		}
	}

	@RequiresApi(Build.VERSION_CODES.TIRAMISU)
	fun requestPostNotification(context:Context){
		if (ActivityCompat.checkSelfPermission(
				context,
				Manifest.permission.POST_NOTIFICATIONS
			) != PackageManager.PERMISSION_GRANTED
		) {
			ActivityCompat.requestPermissions(
				context as Activity ,
				arrayOf(
					Manifest.permission.POST_NOTIFICATIONS
				),
				110
			)
		}
	}
}