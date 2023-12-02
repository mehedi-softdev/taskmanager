package com.innovatenestlabs.taskmanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovatenestlabs.taskmanager.models.Task
import com.innovatenestlabs.taskmanager.repository.TaskRepository
import com.innovatenestlabs.taskmanager.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    val taskResponse: LiveData<Response<Task>> get() = taskRepository.taskResponse

    fun insertTask(task: Task) {
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }
    }

    fun validateTaskInput(task: Task): Pair<Boolean, String> {
        var result = Pair(true, "")
        val filedRequired = "All Field is required"
        val currentDateObj = Date()
        if (task.name.isEmpty()) {
            result = Pair(false, filedRequired)
        } else if (task.desc.isEmpty()) {
            result = Pair(false, filedRequired)
        } else if (task.date.time <= currentDateObj.time) {
            result = Pair(false, "Pick a valid Date Time")
        }
        return result
    }
}