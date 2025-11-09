package com.example.todoapp.viewmodel

import androidx.lifecycle.*
import com.example.todoapp.data.local.TaskEntity
import com.example.todoapp.data.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val allTasks: LiveData<List<TaskEntity>> = repository.getAllTasks().asLiveData()

    fun insertTask(task: TaskEntity) = viewModelScope.launch {
        repository.insertTask(task)
    }

    fun updateTask(task: TaskEntity) = viewModelScope.launch {
        repository.updateTask(task)
    }

    fun deleteTask(task: TaskEntity) = viewModelScope.launch {
        repository.deleteTask(task)
    }

    fun deleteCompletedTasks() = viewModelScope.launch {
        repository.deleteCompletedTasks()
    }

    fun searchTasks(query: String): LiveData<List<TaskEntity>> {
        return repository.searchTasks(query)
    }
}

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
