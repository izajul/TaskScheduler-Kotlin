package com.example.androidtaskscheduler.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtaskscheduler.adapter.SchedulerListAdapter
import com.example.androidtaskscheduler.databinding.ActivityMainBinding
import com.example.androidtaskscheduler.models.TaskModel
import com.example.androidtaskscheduler.ui.dialog.DatePickerDialog
import com.example.androidtaskscheduler.ui.dialog.TimePickDialog
import com.example.androidtaskscheduler.util.AdapterCallBack
import com.example.androidtaskscheduler.util.DialogCallBack
import com.example.androidtaskscheduler.util.Functions.visibilityStatus
import com.example.androidtaskscheduler.viewModels.TaskListViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AdapterCallBack<TaskModel> {
    private val TAG = "MainActivity_TAG"

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<TaskListViewModel>()

    private var mList: MutableList<TaskModel> = ArrayList()

    private var disposer = CompositeDisposable()

    private lateinit var mAdapter: SchedulerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAdapter = SchedulerListAdapter(mList, this)

        binding.listRC.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
        }

        getTaskList()
    }

    private fun getTaskList() {
        disposer.add(viewModel.taskList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    Log.d(TAG, "getTaskList: ${list.size}")
                    mList.clear()
                    mList.addAll(list)
                    // updateUI()
                },
                { err ->
                    Log.e(TAG, "getTaskList: ${err.message}")
                }
            ))
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateUI() {
        binding.progress.visibilityStatus(false)
        mAdapter.notifyDataSetChanged()
        binding.notFoundTV.visibilityStatus(mList.isEmpty())
        binding.listRC.visibilityStatus(mList.isNotEmpty())
    }

    override fun onStop() {
        disposer.clear()
        super.onStop()
    }

    override fun onDelete(item: TaskModel) {
        TODO("Not yet implemented")
    }

    override fun onUpdate(item: TaskModel) {
        Log.d(TAG, "onUpdate: Clicking for update")
        DatePickerDialog(object : DialogCallBack{
            override fun onSelect(date: Any) {
                super.onSelect(date)
                Log.d(TAG, "onSelect: $date")
                TimePickDialog(object : DialogCallBack{
                    override fun onSelect(time: Any) {
                        super.onSelect(time)
                        val dateTime = "$date $time:00"
                        Log.d(TAG, "onSelect: $dateTime")
                        item.time = dateTime
                        updateTime(item)
                    }
                }).show(supportFragmentManager,"timePicker")
            }
        }).show(supportFragmentManager, "datePicker")
    }

    private fun updateTime(item: TaskModel) {
        disposer.add(
            viewModel.update(item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "updateTime: done")
            },{
                Log.e(TAG, "updateTime: failed")
            }
            )
        )
    }

}