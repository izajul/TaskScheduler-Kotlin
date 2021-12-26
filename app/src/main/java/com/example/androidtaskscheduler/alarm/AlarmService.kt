package com.example.androidtaskscheduler.alarm

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.fragment.app.viewModels
import com.example.androidtaskscheduler.MyApplication.Companion.CHANNEL_ID
import com.example.androidtaskscheduler.R
import com.example.androidtaskscheduler.alarm.AlarmReceiver.Companion.PACKAGENAME
import com.example.androidtaskscheduler.alarm.AlarmReceiver.Companion.SCHEDULEID
import com.example.androidtaskscheduler.alarm.AlarmReceiver.Companion.TASKNAME
import com.example.androidtaskscheduler.repositories.TaskRepository
import com.example.androidtaskscheduler.ui.MainActivity
import com.example.androidtaskscheduler.viewModels.TaskListViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class AlarmService : Service() {

    private val TAG = "AlarmService"

    @Inject
    lateinit var taskRepo: TaskRepository

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: Service Created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, FLAG_MUTABLE)

        val taskName = intent?.getStringExtra(TASKNAME)
        val pkgName = intent?.getStringExtra(PACKAGENAME)
        val scheduleId = intent?.getIntExtra(SCHEDULEID,0)?:0

        taskRepo.getTaskById(scheduleId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {task->
                    if(task.doneStatus) return@subscribe
                    Log.d(TAG, "onStartCommand: ${task.packageName}")
                    task.doneStatus = true
                    taskRepo
                        .update(task)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                        {
                            Log.d(TAG, "onStartCommand: done..")
                        },
                        {err->
                            Log.e(TAG, "onStartCommand: failed..",err )
                        }
                    )
                },
                {err->
                    Log.e(TAG, "onStartCommand: task Not found",err )
                }
            )

        val startIntent = pkgName?.let { packageManager.getLaunchIntentForPackage(it) }
        if (startIntent != null) {
            // We found the activity now start the activity
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                application.startActivity(intent)
            }catch (e:Exception){
                Log.e(TAG, "onStartCommand: err ", )
            }

            Log.e(TAG, "onStartCommand: $taskName is started")
        } else {
            Log.e(TAG, "onStartCommand: $taskName is starting failed")
        }

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("$taskName is Started")
            .setContentText("$taskName Schedule Starting done.")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .build()


        startForeground(1, notification)

        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


}