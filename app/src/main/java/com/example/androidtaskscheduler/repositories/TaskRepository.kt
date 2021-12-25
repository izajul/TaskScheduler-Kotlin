package com.example.androidtaskscheduler.repositories

import androidx.room.*
import com.example.androidtaskscheduler.models.TaskModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface TaskRepository {
    fun getTaskById(id: String): Flowable<TaskModel>
    fun getAllTasks(): Flowable<List<TaskModel>>
    fun insertUser(task: TaskModel): Completable
    fun delete(task:TaskModel): Completable
    fun update(task:TaskModel): Completable
}