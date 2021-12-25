package com.example.androidtaskscheduler.modules

import com.example.androidtaskscheduler.repositories.TaskRepoImplement
import com.example.androidtaskscheduler.repositories.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TaskRepoModule {
    @Binds
    abstract fun provideTaskRepo(taskRepoImpl: TaskRepoImplement): TaskRepository
}