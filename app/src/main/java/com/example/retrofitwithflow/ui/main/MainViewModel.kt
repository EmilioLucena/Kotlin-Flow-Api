package com.example.retrofitwithflow.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitwithflow.data.model.TodoItem
import com.example.retrofitwithflow.data.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _todos = MutableLiveData<TodoResponse>()
    val todos: LiveData<TodoResponse>
        get() = _todos
    lateinit var myFlow: Flow<List<TodoItem>>


    fun getTodos() {
        val repository = TodoRepository()
        viewModelScope.launch {
            repository.getTodos().also {
                myFlow = it
            }
                .collect { todoList ->
                _todos.value = todoList
            }
        }
    }
}