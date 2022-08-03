package com.example.retrofitwithflow.data.repository

import com.example.retrofitwithflow.data.model.TodoItem
import com.example.retrofitwithflow.ui.main.TodoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

//this is a simplified version of
//https://levelup.gitconnected.com/android-basic-app-using-mvvm-hilt-coroutines-flow-retrofit-and-coil-433763542ee0

class TodoRepository {
    suspend fun getTodos(): Flow<TodoResponse> {
        return flow {
            val todos = RetrofitInstance.api.getTodos()
            emit(todos)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTodosFromResponse(): Flow<NetworkResult<TodoResponse>> {
        return flow {
            val response = RetrofitInstance.api.getTodosResponse()
            emit(handleResponse(response))
        }.flowOn(Dispatchers.IO)
    }

    private fun handleResponse(response: Response<TodoResponse>): NetworkResult<TodoResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return NetworkResult.Success(it)
            }
        }
        return NetworkResult.Error(response.message())
    }
}