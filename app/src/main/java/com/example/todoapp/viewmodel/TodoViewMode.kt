package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Todo
import com.example.todoapp.data.TodoDatabase
import com.example.todoapp.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TodoRepository

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val allTodos: kotlinx.coroutines.flow.Flow<List<Todo>>

    init {
        val todoDao = TodoDatabase.getDatabase(application).todoDao()
        repository = TodoRepository(todoDao)
        allTodos = repository.getAllTodos()
    }

    fun searchTodos(query: String): kotlinx.coroutines.flow.Flow<List<Todo>> {
        _searchQuery.value = query
        return if (query.isEmpty()) {
            repository.getAllTodos()
        } else {
            repository.searchTodos(query)
        }
    }

    fun insertTodo(title: String, description: String) {
        viewModelScope.launch {
            repository.insertTodo(
                Todo(
                    title = title,
                    description = description
                )
            )
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            repository.deleteTodo(todo)
        }
    }

    fun toggleTodoCompletion(todo: Todo) {
        viewModelScope.launch {
            repository.updateTodo(todo.copy(isCompleted = !todo.isCompleted))
        }
    }
}