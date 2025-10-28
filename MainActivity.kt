package com.example.todolist
package com.example.todoapp.ui

import android.app.AlertDialog
import android.view.LayoutInflater
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.ActivityMainBinding
import org.json.JSONArray
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewMod
import com.example.todoapp.viewmodel.TaskViewModel







class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val tasks = mutableListOf<String>()
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadTasks()
        adapter = TaskAdapter(tasks) { saveTasks() }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.addButton.setOnClickListener {
            val task = binding.taskInput.text.toString()
            if (task.isNotEmpty()) {
                tasks.add(task)
                adapter.notifyItemInserted(tasks.size - 1)
                binding.taskInput.text.clear()
                saveTasks()
            }
        }
    }

    private fun saveTasks() {
        val sharedPref = getSharedPreferences("TodoPrefs", Context.MODE_PRIVATE)
        sharedPref.edit().putString("tasks", JSONArray(tasks).toString()).apply()
    }

    private fun loadTasks() {
        val sharedPref = getSharedPreferences("TodoPrefs", Context.MODE_PRIVATE)
        val json = sharedPref.getString("tasks", null) ?: return
        val jsonArray = JSONArray(json)
        for (i in 0 until jsonArray.length()) {
            tasks.add(jsonArray.getString(i))
        }
    }
}



private lateinit var binding: ActivityMainBinding
    private val viewModel: TaskViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TaskAdapter(emptyList(),
            onItemClick = { task -> editTask(task) },
            onItemLongClick = { task -> deleteTask(task) }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.allTasks.observe(this) { tasks ->
            adapter.updateTasks(tasks)
        }

        binding.fabAdd.setOnClickListener { showAddDialog() }
    }

    private fun showAddDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null)
        val etTitle = dialogView.findViewById<EditText>(R.id.etTitle)
        val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)
        val etDate = dialogView.findViewById<EditText>(R.id.etDate)

        AlertDialog.Builder(this)
            .setTitle("Add New Task")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val task = Task(
                    title = etTitle.text.toString(),
                    description = etDescription.text.toString(),
                    dueDate = etDate.text.toString()
                )
                viewModel.insert(task)
                Toast.makeText(this, "Task Added ✅", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun editTask(task: Task) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_task, null)
        val etTitle = dialogView.findViewById<EditText>(R.id.etTitle)
        val etDescription = dialogView.findViewById<EditText>(R.id.etDescription)
        val etDate = dialogView.findViewById<EditText>(R.id.etDate)

        etTitle.setText(task.title)
        etDescription.setText(task.description)
        etDate.setText(task.dueDate)

        AlertDialog.Builder(this)
            .setTitle("Edit Task")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val updatedTask = task.copy(
                    title = etTitle.text.toString(),
                    description = etDescription.text.toString(),
                    dueDate = etDate.text.toString()
                )
                viewModel.update(updatedTask)
                Toast.makeText(this, "Task Updated ✏️", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteTask(task: Task) {
        AlertDialog.Builder(this)
            .setTitle("Delete Task?")
            .setMessage("Are you sure you want to delete '${task.title}'?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.delete(task)

Toast.makeText(this, "Task Deleted 🗑️", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)
            .show()
    }
}

private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TaskAdapter
    private val viewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(TaskRepository(TaskDatabase.getDatabase(this).taskDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeTasks()
        setupFab()
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(listOf()) { /* handle click later if needed */ }
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTasks.adapter = adapter
    }

    private fun observeTasks() {
        viewModel.allTasks.observe(this) { tasks ->
            adapter.updateList(tasks)
            updateTotalCount(tasks)
        }
    }

    private fun setupFab() {
        binding.fabAddTask.setOnClickListener {
            AddEditTaskDialog(this) { task ->
                lifecycleScope.launch {
                    viewModel.insertTask(task)
                }
            }.show()
        }
    }

    private fun updateTotalCount(tasks: List<TaskEntity>) {
        binding.tvTitle.text = "My Smart To-Do List (${tasks.size} tasks)"
    }
}

