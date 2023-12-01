package com.innovatenestlabs.taskmanager.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.innovatenestlabs.taskmanager.models.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    suspend fun getTaskList(): List<Task>

    @Query("SELECT * FROM tasks WHERE tasks.id=:taskId")
    suspend fun getTaskById(taskId: Int): Task


    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

}