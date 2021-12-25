package com.example.androidtaskscheduler.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "tasks_table")
data class TaskModel(
    @ColumnInfo(name = "package")
    val packageName: String,
    @ColumnInfo(name = "done_status")
    val doneStatus: Boolean,
    @ColumnInfo(name = "schedule_status")
    val status: Boolean,
    val time: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}