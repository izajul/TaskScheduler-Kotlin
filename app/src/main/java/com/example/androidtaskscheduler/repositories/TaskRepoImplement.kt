package com.example.androidtaskscheduler.repositories

import com.example.androidtaskscheduler.database.TaskDAO
import com.example.androidtaskscheduler.models.TaskModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class TaskRepoImplement
@Inject constructor(private val taskDAO: TaskDAO) : TaskRepository {

    override fun getTaskById(id: String): Flowable<TaskModel> {
        TODO("Not yet implemented")
    }

    override fun getAllTasks(): Flowable<List<TaskModel>> {
        TODO("Not yet implemented")
    }

    override fun insertUser(task: TaskModel): Completable {
        TODO("Not yet implemented")
    }

    override fun delete(task: TaskModel): Completable {
        TODO("Not yet implemented")
    }

    override fun update(task: TaskModel): Completable {
        TODO("Not yet implemented")
    }
}