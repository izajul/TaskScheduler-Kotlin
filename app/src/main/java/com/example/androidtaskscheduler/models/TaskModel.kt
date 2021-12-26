package com.example.androidtaskscheduler.models

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.androidtaskscheduler.alarm.AlarmReceiver
import com.example.androidtaskscheduler.alarm.AlarmReceiver.Companion.PACKAGENAME
import com.example.androidtaskscheduler.alarm.AlarmReceiver.Companion.SCHEDULEID
import com.example.androidtaskscheduler.alarm.AlarmReceiver.Companion.TASKNAME
import com.example.androidtaskscheduler.util.Functions
import java.util.*

@Entity(tableName = "tasks_table")
data class TaskModel(
    @ColumnInfo(name = "name")
    var taskName: String,
    @ColumnInfo(name = "package")
    var packageName: String,
    @ColumnInfo(name = "done_status")
    var doneStatus: Boolean,
    @ColumnInfo(name = "schedule_status")
    var scheduleStatus: Boolean,
    var time: String
) {
    @Ignore
    private val TAG = "TaskModel"

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "schedule_id")
    var scheduleId : Int = (1000..9999).random()

    fun startSchedule(context: Context){
        if (time.isEmpty()) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(TASKNAME, this.taskName)
        intent.putExtra(PACKAGENAME, this.packageName)
        intent.putExtra(SCHEDULEID, this.scheduleId)

        val alarmPendingIntent = PendingIntent.getBroadcast(context, scheduleId, intent, FLAG_MUTABLE)

        val calendar = Calendar.getInstance()
        calendar.time = Functions.getDateFromString(this.time)

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            alarmPendingIntent
        )

        scheduleStatus = true

        Log.d(TAG, "startSchedule: Alarm Started for $taskName")
    }

    fun stopSchedule(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, scheduleId, intent, FLAG_MUTABLE)
        alarmManager.cancel(alarmPendingIntent)
        this.scheduleStatus = false
        Log.d(TAG, "stopSchedule: Alarm Stopped for $taskName")
    }
}