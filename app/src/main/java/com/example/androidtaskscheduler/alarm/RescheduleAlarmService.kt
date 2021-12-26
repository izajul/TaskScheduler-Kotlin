package com.example.androidtaskscheduler.alarm

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleService
import com.example.androidtaskscheduler.repositories.TaskRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RescheduleAlarmService : LifecycleService() {
    private val TAG = "RescheduleAlarmService"

    @Inject
    lateinit var taskRepo: TaskRepository

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        taskRepo.getAllTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    Log.d(TAG, "getTaskList: ${list.size}")
                    for (a in list) {
                        if (a.scheduleStatus) {
                            a.startSchedule(applicationContext)
                        }
                    }
                },
                { err ->
                    Log.e(TAG, "getTaskList: ${err.message}")
                }
            )
        return Service.START_STICKY
    }


    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }
}