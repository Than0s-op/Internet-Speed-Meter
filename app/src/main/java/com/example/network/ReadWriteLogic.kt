package com.example.network

import android.content.Context
import android.content.SharedPreferences

class ReadWriteLogic(context: Context) {
    //   check local storage permission
    private val helper: SharedPreferences =
        context.getSharedPreferences("NetworkData", Context.MODE_PRIVATE)

    fun write(fileName: String, fileContent: Set<String>) {
        val editor: SharedPreferences.Editor = helper.edit()
//        editor.putString(fileName, fileContent)
        editor.putStringSet(fileName,fileContent)
        editor.apply()
    }

    fun read(fileName: String): Set<String> {
        return helper.getStringSet(fileName, setOf("-"))!!
    }
}