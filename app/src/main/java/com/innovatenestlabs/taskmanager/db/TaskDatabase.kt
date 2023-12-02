package com.innovatenestlabs.taskmanager.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.innovatenestlabs.taskmanager.models.Task
import com.innovatenestlabs.taskmanager.utils.DatabaseConverter

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
}