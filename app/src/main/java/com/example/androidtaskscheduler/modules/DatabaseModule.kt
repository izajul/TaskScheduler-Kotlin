package com.example.androidtaskscheduler.modules

import android.content.Context
import androidx.room.Room
import com.example.androidtaskscheduler.database.TaskDAO
import com.example.androidtaskscheduler.database.TaskDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideChannelDao(dataBase: TaskDataBase): TaskDAO{
        return dataBase.userDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): TaskDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            TaskDataBase::class.java,
            "task_schedule.db"
        ).build()
    }
}