package com.example.androidtaskscheduler.database

import androidx.room.*
import com.example.androidtaskscheduler.models.TaskModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface TaskDAO {

    @Query("SELECT * FROM tasks_table WHERE schedule_id = :id")
    fun getTaskById(id: Int): Flowable<TaskModel>

    @Query("SELECT * FROM tasks_table")
    fun getAllTasks(): Flowable<List<TaskModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(task: TaskModel): Completable

    @Delete
    fun delete(task:TaskModel): Completable

    @Update
    fun update(task:TaskModel): Completable
}