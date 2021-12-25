package com.example.androidtaskscheduler.repositories

import com.example.androidtaskscheduler.database.TaskDAO
import com.example.androidtaskscheduler.models.TaskModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class TaskRepoImplement
@Inject constructor(private val taskDAO: TaskDAO) : TaskRepository {

    override fun getTaskById(id: String): Flowable<TaskModel> {
        return taskDAO.getTaskById(id)
    }

    override fun getAllTasks(): Flowable<List<TaskModel>> {
        return taskDAO.getAllTasks()
    }

    override fun insertUser(task: TaskModel): Completable {
        return taskDAO.insertUser(task)
    }

    override fun delete(task: TaskModel): Completable {
        return taskDAO.delete(task)
    }

    override fun update(task: TaskModel): Completable {
        return taskDAO.update(task)
    }
}