package com.example.androidtaskscheduler.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks_table")
data class TaskModel(
    @ColumnInfo(name = "package")
    var packageName: String,
    @ColumnInfo(name = "done_status")
    var doneStatus: Boolean,
    @ColumnInfo(name = "schedule_status")
    var status: Boolean,
    var time: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}