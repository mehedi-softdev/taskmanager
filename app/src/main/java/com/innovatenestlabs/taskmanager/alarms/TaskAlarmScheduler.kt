package com.innovatenestlabs.taskmanager.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.innovatenestlabs.taskmanager.models.Task
import com.innovatenestlabs.taskmanager.utils.Constants.TASK_MODEL
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TaskAlarmScheduler @Inject constructor(@ApplicationContext private val context: Context) :
    AlarmScheduler {
    @RequiresApi(Build.VERSION_CODES.M)
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun schedule(task: Task) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(TASK_MODEL, Gson().toJson(task))
        }
        // trigger even device is in low power mode
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            task.date.time,
            PendingIntent.getBroadcast(
                context,
                task.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun cancel(task: Task) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                task.id,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}