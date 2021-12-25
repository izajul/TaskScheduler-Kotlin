package com.example.androidtaskscheduler.ui.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.androidtaskscheduler.util.DialogCallBack
import java.util.*

class DatePickerDialog(private val listener: DialogCallBack) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {
    private val TAG = "DatePickerDialog"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireActivity(), this, year, month, day).apply {
            this.datePicker.minDate = Date().time
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        Log.d(TAG, "onDateSet: $year-${month+1}-$day")
        listener.onSelect("$year-${month + 1}-$day")
        dismiss()
    }


}