package com.example.androidtaskscheduler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.androidtaskscheduler.databinding.ActivityMainBinding
import com.example.androidtaskscheduler.viewModels.TaskListViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity_TAG"

    private lateinit var binder: ActivityMainBinding

    private val viewModel by viewModels<TaskListViewModel>()

    private var disposer = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMainBinding.inflate(layoutInflater)

        getTaskList()
    }

    private fun getTaskList() {
        disposer.add(viewModel.taskList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    Log.d(TAG, "getTaskList: ${list.size}")
                },
                { err ->
                    Log.e(TAG, "getTaskList: ${err.message}", )
                }
            ))
    }

    override fun onStop() {
        disposer.clear()
        super.onStop()
    }
}