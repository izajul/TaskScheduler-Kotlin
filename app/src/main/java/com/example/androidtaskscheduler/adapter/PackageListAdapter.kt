package com.example.androidtaskscheduler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtaskscheduler.databinding.PackageListRowBinding
import com.example.androidtaskscheduler.databinding.ScheduleListRowBinding
import com.example.androidtaskscheduler.models.PackageModel
import com.example.androidtaskscheduler.models.TaskModel
import com.example.androidtaskscheduler.util.Functions
import com.example.androidtaskscheduler.util.AdapterCallBack
import com.example.androidtaskscheduler.util.Functions.visibilityStatus

class PackageListAdapter(
    private val list: List<PackageModel>,
    val listener: AdapterCallBack<PackageModel>
) :
    RecyclerView.Adapter<PackageListAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: PackageListRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        PackageListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = list[position]
        val view = holder.binding

        view.packageNameTV.text = data.name
        view.image.setImageDrawable(data.icon)
        view.scheduleBtn.setOnClickListener { listener.onSet(data) }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}