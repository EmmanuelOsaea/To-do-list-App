package com.example.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemTaskBinding

class TaskAdapter(private val tasks: MutableList<String>, private val onUpdate: () -> Unit) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.binding.taskText.text = tasks[position]
        holder.binding.deleteButton.setOnClickListener {
            tasks.removeAt(position)
            notifyItemRemoved(position)
            onUpdate()
        }
    }
}
holder.tvDescription.text = task.description
        holder.tvDate.text = "Due: ${task.dueDate}"

        holder.itemView.setOnClickListener { onItemClick(task) }
        holder.itemView.setOnLongClickListener {
            onItemLongClick(task)
            true
        }
    }


    


  fun updateTasks(newList: List<Task>) {
        taskList = newList
        notifyDataSetChanged()
    }
}
  
