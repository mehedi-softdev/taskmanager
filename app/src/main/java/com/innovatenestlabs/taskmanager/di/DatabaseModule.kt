package com.innovatenestlabs.taskmanager.di

import android.content.Context
import androidx.room.Room
import com.innovatenestlabs.taskmanager.db.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule  {
    @Singleton
    @Provides
    fun providesTaskDatabase(@ApplicationContext context: Context): TaskDatabase {
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java, "taskdatabase.db"
        ).build()
    }
}