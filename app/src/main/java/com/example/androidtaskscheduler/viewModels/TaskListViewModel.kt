package com.example.androidtaskscheduler.viewModels

import androidx.lifecycle.ViewModel
import com.example.androidtaskscheduler.models.TaskModel
import com.example.androidtaskscheduler.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel
@Inject constructor(private val taskRepo: TaskRepository) : ViewModel() {
    val taskList = taskRepo.getAllTasks()
    fun addNew(task:TaskModel) = taskRepo.insertUser(task)
    fun getTaskById(id: String) = taskRepo.getTaskById(id)
    fun delete(task:TaskModel) = taskRepo.delete(task)
    fun update(task:TaskModel) = taskRepo.update(task)
    fun updateSchedule(task:TaskModel): Completable {

        /// todo: need to start / Stop Alarm Schedule for this task
        /// todo: will generate Alarm manager later on
        task.scheduleStatus = !task.scheduleStatus
        return taskRepo.update(task)
    }
}