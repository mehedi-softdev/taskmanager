package com.innovatenestlabs.taskmanager.utils

import androidx.room.TypeConverter
import java.util.Date

class DatabaseConverter {
    @TypeConverter
    fun dateToLong(value: Date): Long = value.time

    @TypeConverter
    fun longToDate(value: Long): Date = Date(value)
}