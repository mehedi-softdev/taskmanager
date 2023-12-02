package com.innovatenestlabs.taskmanager

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.innovatenestlabs.taskmanager.utils.Constants.NOTIFICATION_CHANNEL_ID
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TaskApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Task Remainder Channel"
            val description: String = "Channel for Task Alarm Manager"
            val importance: Int = NotificationManager.IMPORTANCE_HIGH
            val channel: NotificationChannel =
                NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager: NotificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}