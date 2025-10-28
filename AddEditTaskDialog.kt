package com.example.todoapp.ui

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.todoapp.R
import com.example.todoapp.data.local.TaskEntity

class AddEditTaskDialog(
    private val context: Context,
    private val onSave: (TaskEntity) -> Unit
) {
    fun show() {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_edit_task, null)
        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etDesc = view.findViewById<EditText>(R.id.etDescription)
        val spinnerPriority = view.findViewById<Spinner>(R.id.spinnerPriority)

        val priorities = listOf("High", "Medium", "Low")
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, priorities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPriority.adapter = adapter

        AlertDialog.Builder(context)
            .setTitle("Add Task")
            .setView(view)
            .setPositiveButton("Save") { _, _ ->
                val task = TaskEntity(
                    title = etTitle.text.toString(),
                    description = etDesc.text.toString(),
                    priority = spinnerPriority.selectedItem.toString()
                )
                onSave(task)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
