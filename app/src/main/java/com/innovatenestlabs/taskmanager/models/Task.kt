package com.innovatenestlabs.taskmanager.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var name: String,
    var date: Date,
    var isComplete: Boolean
)