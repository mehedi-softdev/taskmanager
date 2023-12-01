package com.innovatenestlabs.taskmanager.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

object AppConverters {
    fun getDateTime(date: Date): String {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
                val localDateTime =
                    LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
                localDateTime.format(formatter)
            } else {
                val format = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                format.format(date)
            }
        } catch (e: Exception) {
            ""
        }
    }
}