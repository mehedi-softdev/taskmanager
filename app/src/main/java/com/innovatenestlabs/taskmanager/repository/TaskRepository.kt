package com.innovatenestlabs.taskmanager.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.innovatenestlabs.taskmanager.db.TaskDatabase
import com.innovatenestlabs.taskmanager.models.Task
import com.innovatenestlabs.taskmanager.utils.Response
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDatabase: TaskDatabase) {
    private var _taskResponse: MutableLiveData<Response<Task>> = MutableLiveData()
    val taskResponse: LiveData<Response<Task>> get() = _taskResponse
    suspend fun insertTask(task: Task) {
        _taskResponse.postValue(Response.Loading())
        try {
            taskDatabase.getTaskDao().insertTask(task)
            _taskResponse.postValue(Response.Success(data = task))
        } catch (e: Exception) {
            _taskResponse.postValue(Response.Error(e.message.toString()))
        }
    }
}