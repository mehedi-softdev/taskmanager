package com.innovatenestlabs.taskmanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovatenestlabs.taskmanager.models.Task
import com.innovatenestlabs.taskmanager.repository.TaskRepository
import com.innovatenestlabs.taskmanager.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayTasksViewModel @Inject constructor(private val taskRepository: TaskRepository) :
    ViewModel() {
    val taskListResponse: LiveData<Response<List<Task>>> get() = taskRepository.taskListResponse


    fun loadTaskList() {
        viewModelScope.launch {
            taskRepository.getTaskList()
        }
    }
}