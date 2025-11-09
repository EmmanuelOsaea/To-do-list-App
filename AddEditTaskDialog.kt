package com.example.todoapp.ui

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.todoapp.data.local.TaskEntity
import com.example.todoapp.databinding.DialogAddEditTaskBinding

class AddEditTaskDialog(
    private val context: Context,
    private val onSave: (TaskEntity) -> Unit
) {
    fun show() {
        val binding = DialogAddEditTaskBinding.inflate(LayoutInflater.from(context))

        val priorities = listOf("High", "Medium", "Low")
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPriority.adapter = adapter

        AlertDialog.Builder(context)
            .setTitle("Add Task")
            .setView(binding.root)
            .setPositiveButton("Save") { _, _ ->
                val title = binding.etTitle.text.toString()
                if (title.isNotBlank()) {
                    val task = TaskEntity(
                        title = title,
                        description = binding.etDescription.text.toString(),
                        priority = binding.spinnerPriority.selectedItem.toString()
                    )
                    onSave(task)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
