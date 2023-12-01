package com.innovatenestlabs.taskmanager.repository

import com.innovatenestlabs.taskmanager.db.TaskDatabase
import com.innovatenestlabs.taskmanager.models.Task
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDatabase: TaskDatabase) {
    suspend fun insertTask(task: Task) {
        taskDatabase.getTaskDao().insertTask(task)
    }
}