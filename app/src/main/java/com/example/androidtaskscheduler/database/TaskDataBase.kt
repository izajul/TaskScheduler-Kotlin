package com.example.androidtaskscheduler.database

import android.content.Context
import androidx.room.*
import com.example.androidtaskscheduler.models.TaskModel

@Database(entities = [TaskModel::class],version = 1)
abstract class TaskDataBase : RoomDatabase(){
    abstract fun userDao(): TaskDAO
}