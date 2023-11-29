package com.innovatenestlabs.taskmanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.innovatenestlabs.taskmanager.databinding.TaskListItemLayoutBinding
import com.innovatenestlabs.taskmanager.models.Task

class TaskListAdapter: ListAdapter<Task, TaskListAdapter.TaskListViewHolder>(TaskListDiffUtilCallBack()) {

    inner class TaskListViewHolder(private val binding: TaskListItemLayoutBinding): ViewHolder(binding.root) {
        fun bind(task: Task) {

        }
    }

    class TaskListDiffUtilCallBack: DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
          return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =TaskListItemLayoutBinding.inflate(inflater, parent, false)
        return TaskListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val task: Task = getItem(position)
        holder.bind(task)
    }
}