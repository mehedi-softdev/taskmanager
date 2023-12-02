package com.innovatenestlabs.taskmanager.alarms

import com.innovatenestlabs.taskmanager.models.Task

interface AlarmScheduler {
    fun schedule(task: Task)
    fun cancel(task: Task)
}