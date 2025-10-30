package com.example.todoapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.RecyclerView
import com.example.todoapp.data.local.TaskEntity
import com.google.android.material.card.MaterialCardView
import android.widget.TextView
import com.example.todoapp.databinding.ItemTaskBinding

class TaskAdapter(
    private var tasks: List<TaskEntity>,
    private val onClick: (TaskEntity) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvTaskTitle)
        private val desc: TextView = itemView.findViewById(R.id.tvTaskDescription)
        private val priority: TextView = itemView.findViewById(R.id.tvPriority)
        private val card: MaterialCardView = itemView as MaterialCardView

        fun bind(task: TaskEntity) {
            title.text = task.title
            desc.text = task.description
            priority.text = "Priority: ${task.priority}"

            card.setOnClickListener { onClick(task) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    fun updateList(newTasks: List<TaskEntity>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}
