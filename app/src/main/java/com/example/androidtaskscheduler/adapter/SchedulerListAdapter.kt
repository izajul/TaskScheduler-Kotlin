package com.example.androidtaskscheduler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtaskscheduler.databinding.ScheduleListRowBinding
import com.example.androidtaskscheduler.models.TaskModel
import com.example.androidtaskscheduler.util.Functions
import com.example.androidtaskscheduler.util.AdapterCallBack
import com.example.androidtaskscheduler.util.Functions.visibilityStatus

class SchedulerListAdapter(
    private val list: List<TaskModel>,
    val listener: AdapterCallBack<TaskModel>
) :
    RecyclerView.Adapter<SchedulerListAdapter.MyViewHolder>() {


    class MyViewHolder(val binding: ScheduleListRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        ScheduleListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.binding.scheduleUpdateBtn.setOnClickListener {
            listener.onUpdate(TaskModel("",false,false,""))
        }
        return

        val data = list[position]
        val view = holder.binding

        view.packageNameTV.text = data.packageName
        view.timeTV.text = if (data.time.isNotEmpty())
            Functions.getFormattedDate(data.time, "hh:mm a, dd MMM yy")
        else ""

        if (data.doneStatus){
            view.scheduleUpdateBtn.apply {
                isEnabled = false
                text = "Task Completed"
            }
            view.scheduleUpdateBtn.visibilityStatus(false)
        }

        view.scheduleUpdateBtn.setOnClickListener {
            listener.onUpdate(data)
        }

        view.scheduleDeleteBtn.setOnClickListener {
            listener.onDelete(data)
        }

//        holder.binding

    }

    override fun getItemCount(): Int {
        return 10 //list.size
    }
}