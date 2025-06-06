package com.example.todoapp.repository

import com.example.todoapp.data.Todo
import com.example.todoapp.data.TodoDao
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {

    fun getAllTodos(): Flow<List<Todo>> = todoDao.getAllTodos()

    fun searchTodos(query: String): Flow<List<Todo>> = todoDao.searchTodos(query)

    suspend fun insertTodo(todo: Todo) = todoDao.insertTodo(todo)

    suspend fun updateTodo(todo: Todo) = todoDao.updateTodo(todo)

    suspend fun deleteTodo(todo: Todo) = todoDao.deleteTodo(todo)

    suspend fun deleteTodoById(id: Int) = todoDao.deleteTodoById(id)
}