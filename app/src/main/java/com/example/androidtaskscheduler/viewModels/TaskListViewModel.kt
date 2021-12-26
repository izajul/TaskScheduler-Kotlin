package com.example.androidtaskscheduler.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.androidtaskscheduler.models.TaskModel
import com.example.androidtaskscheduler.repositories.TaskRepository
import com.example.androidtaskscheduler.util.Functions
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class TaskListViewModel
@Inject constructor(private val taskRepo: TaskRepository, val context: Application) : ViewModel() {

    private val TAG = "TaskListViewModel"

    val taskList = taskRepo.getAllTasks()

    fun addNew(task: TaskModel): Completable {
        var isTimeMatched = false

        if (isTimeMatched) return Completable.error(Throwable("This Time Schedule Already Exist, Please Set Different"))

        if (task.scheduleStatus) task.startSchedule(context)
        else task.stopSchedule(context)
        return taskRepo.insertUser(task)
    }

    fun getTaskById(id: Int) = taskRepo.getTaskById(id)

    fun delete(task: TaskModel) = taskRepo.delete(task)

    fun update(task: TaskModel) = taskRepo.update(task)

    fun updateSchedule(task: TaskModel): Completable {
        task.scheduleStatus = !task.scheduleStatus
        if (task.scheduleStatus) task.startSchedule(context)
        else task.stopSchedule(context)
        return taskRepo.update(task)
    }
}