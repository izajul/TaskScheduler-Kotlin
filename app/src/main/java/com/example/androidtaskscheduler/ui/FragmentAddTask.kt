package com.example.androidtaskscheduler.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtaskscheduler.adapter.PackageListAdapter
import com.example.androidtaskscheduler.databinding.FragmentAddTaskBinding
import com.example.androidtaskscheduler.models.TaskModel
import com.example.androidtaskscheduler.ui.dialog.DatePickerDialog
import com.example.androidtaskscheduler.ui.dialog.TimePickDialog
import com.example.androidtaskscheduler.util.AdapterCallBack
import com.example.androidtaskscheduler.util.DialogCallBack
import com.example.androidtaskscheduler.viewModels.TaskListViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

import android.content.Intent
import com.example.androidtaskscheduler.models.PackageModel
import com.example.androidtaskscheduler.util.Functions
import com.example.androidtaskscheduler.util.Functions.visibilityStatus
import io.reactivex.rxjava3.core.Completable
import kotlinx.coroutines.*


@AndroidEntryPoint
class FragmentAddTask : Fragment(), AdapterCallBack<PackageModel> {
    private val TAG = "FragmentAddTask"

    private lateinit var binding: FragmentAddTaskBinding

    private var disposer = CompositeDisposable()
    private val viewModel by viewModels<TaskListViewModel>()
    private lateinit var mAdapter: PackageListAdapter
    private var mList: MutableList<PackageModel> = ArrayList()
    private var mTaskList: MutableList<TaskModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        mAdapter = PackageListAdapter(mList, this)
        binding.listRc.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
        getTaskList()
        return binding.root
    }

    private fun getTaskList() {
        disposer.add(
            viewModel.taskList
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { list ->
                        Log.d(TAG, "getTaskList: ${list.size}")
                        mTaskList.clear()
                        mTaskList.addAll(list)
                        showPackageList()
                    },
                    { err ->
                        updateUI()
                        Log.e(TAG, "getTaskList: ${err.message}")
                    }
                )
        )
    }

    @DelicateCoroutinesApi
    @SuppressLint("QueryPermissionsNeeded", "NotifyDataSetChanged")
    private fun showPackageList() {
        val mainIntent = Intent(Intent.ACTION_MAIN)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pm = requireContext().packageManager
        val pkgAppsList = pm.queryIntentActivities(mainIntent, 0)

        /// Using Coroutine Scope
        CoroutineScope(Dispatchers.IO).launch {
            for (pkg in pkgAppsList) {
                val name = pkg.activityInfo.loadLabel(pm)
                val pkgName = pkg.activityInfo.packageName
                val img = pkg.activityInfo.loadIcon(pm)
                if (!checkIfAlreadyHave(pkgName)) {
                    mList.add(PackageModel(name.toString(), pkgName, img))
                }
            }
            withContext(Dispatchers.Main) {
                updateUI()
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateUI() {
        binding.progress.visibilityStatus(false)
        mAdapter.notifyDataSetChanged()
        binding.notFoundTV.visibilityStatus(mList.isEmpty())
        binding.listRc.visibilityStatus(mList.isNotEmpty())
    }

    private fun checkIfAlreadyHave(pkgName: String): Boolean {
        var boolean = false
        for (task in mTaskList) {
            if (pkgName == task.packageName) {
                boolean = true
                break
            }
        }
        return boolean
    }

    override fun onSet(item: PackageModel) {
        super.onSet(item)
        DatePickerDialog(object : DialogCallBack {
            override fun onSelect(date: Any) {
                super.onSelect(date)
                Log.d(TAG, "onSelect: $date")
                TimePickDialog(object : DialogCallBack {
                    override fun onSelect(time: Any) {
                        super.onSelect(time)
                        val dateTime = "$date $time:00"
                        Log.d(TAG, "onSelect: $dateTime")

                        addTask(item, dateTime)
                    }
                }).show(parentFragmentManager, "timePicker")
            }
        }).show(parentFragmentManager, "datePicker")
    }

    private fun addTask(data: PackageModel, dateTime: String) {
        val task = TaskModel(
            data.name,
            data.packageName,
            doneStatus = false,
            scheduleStatus = true,
            dateTime
        )

        if (checkTaskIsExist(task)){
            Toast.makeText(requireContext(),"This Time Schedule Already Exist, Please Set Different",Toast.LENGTH_LONG).show()
            return
        }

        disposer.add(
            viewModel.addNew(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { Log.d(TAG, "addTask: done"); requireActivity().onBackPressed() },
                    {
                        Log.e(TAG, "addTask: adding Failed", it)
                        Toast.makeText(
                            requireContext(),
                            "Adding Failed! ${it.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
        )
    }

    private fun checkTaskIsExist(task: TaskModel): Boolean {
        var isTimeMatched = false
        for (item in mTaskList) {
            if (Functions.getDateFromString(item.time)
                    .compareTo(Functions.getDateFromString(task.time)) == 0
            ){
                isTimeMatched = true
                break
            }
        }
        return isTimeMatched
    }

    override fun onPause() {
        disposer.clear()
        super.onPause()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FragmentAddTask().apply { }
    }
}