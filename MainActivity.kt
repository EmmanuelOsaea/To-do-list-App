package com.example.todolist

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.ActivityMainBinding
import org.json.JSONArray

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
