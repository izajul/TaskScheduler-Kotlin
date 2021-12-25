package com.example.androidtaskscheduler.viewModels

import androidx.lifecycle.ViewModel
import com.example.androidtaskscheduler.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel
@Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {

}