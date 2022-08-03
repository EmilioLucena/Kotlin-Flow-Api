package com.example.retrofitwithflow.data.source.remote.api

import com.example.retrofitwithflow.data.model.TodoItem
import com.example.retrofitwithflow.ui.main.TodoResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET

interface TodoApi {

    @GET("/todos")
    suspend fun getTodos(): TodoResponse

    @GET("/todos")
    suspend fun getTodosResponse(): Response<TodoResponse>
}