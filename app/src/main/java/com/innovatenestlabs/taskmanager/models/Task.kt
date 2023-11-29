package com.innovatenestlabs.taskmanager.models

import java.util.Date

data class Task(
    val id: Int,
    var name: String,
    var date: Date,
    var isComplete: Boolean
)