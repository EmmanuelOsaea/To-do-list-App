class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    val allTasks: LiveData<List<Task>>

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
    }

    fun insert(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(task)
    }

    fun update(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(task)
    }

    fun delete(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(task)
    }
}
