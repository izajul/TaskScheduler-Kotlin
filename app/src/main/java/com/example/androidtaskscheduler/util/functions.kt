package com.example.androidtaskscheduler.util

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.util.Log
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

object Functions {

    @SuppressLint("SimpleDateFormat")
    fun getFormattedDate(dateStr: String, format: String): String {
        var dateT = ""
        val current = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        var date = current.parse(dateStr)
        val formatterDate = SimpleDateFormat(format, Locale.ENGLISH)
        dateT = formatterDate.format(date)
        return dateT
    }

    fun getFormattedDate(date: Date, format: String): String {
        var dateT = ""
        try {
            val formatterDate = SimpleDateFormat(format, Locale.ENGLISH)
            dateT = formatterDate.format(date)
        }catch (e:  Exception){
            e.message?.let { Log.e("date_format", it) };
        }
        return dateT
    }

    fun View.visibilityStatus(status: Boolean){
        this.visibility = if (status) View.VISIBLE else View.GONE
    }
}