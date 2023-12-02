package com.innovatenestlabs.taskmanager.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        // later
        println("Alarm Triggered")
    }
}