package com.example.todoapp.data.repository

import com.example.todoapp.data.local.TaskDao
import com.example.todoapp.data.local.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {
    fun getAllTasks(): Flow<List<TaskEntity>> = dao.getAllTasks()
    suspend fun insert(task: TaskEntity) = dao.insert(task)
}
