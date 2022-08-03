package com.example.retrofitwithflow.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitwithflow.data.model.TodoItem
import com.example.retrofitwithflow.data.repository.NetworkResult
import com.example.retrofitwithflow.data.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NetworkResultMainViewModel : ViewModel() {

    private val _todos = MutableLiveData<NetworkResult<TodoResponse>>()
    val todos: LiveData<NetworkResult<TodoResponse>>
        get() = _todos

    //used to collect at MainActivity instead of through livedata observation
    lateinit var myFlow: Flow<NetworkResult<TodoResponse>>


    fun getTodos() {
        val repository = TodoRepository()
        viewModelScope.launch {
            repository.getTodosFromResponse().also {
                myFlow = it
            }
                .collect { todoResponse ->
                _todos.value = todoResponse
            }
        }
    }
}

typealias TodoResponse = List<TodoItem>