package com.innovatenestlabs.taskmanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovatenestlabs.taskmanager.models.Task
import com.innovatenestlabs.taskmanager.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {
    fun insertTask(task: Task) {
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }
    }

    fun validateTaskInput(task: Task): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (task.name.isEmpty()) {
            result = Pair(false, "Field is required")
        } else if (task.desc.isEmpty()) {
            result = Pair(false, "Field is required")
        }
        return result
    }
}