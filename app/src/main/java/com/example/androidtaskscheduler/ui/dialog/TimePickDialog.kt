package com.example.androidtaskscheduler.ui.dialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.androidtaskscheduler.util.DialogCallBack
import java.util.*

class TimePickDialog(private val listener: DialogCallBack) : DialogFragment(),
    TimePickerDialog.OnTimeSetListener {
    private val TAG = "TimePickerDialog"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        Log.d(TAG, "onTimeSet: $hourOfDay:$minute")
        listener.onSelect("$hourOfDay:$minute")
        dismiss()
    }


}