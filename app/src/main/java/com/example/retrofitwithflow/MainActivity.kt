package com.example.retrofitwithflow

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitwithflow.data.repository.NetworkResult
import com.example.retrofitwithflow.databinding.ActivityMainBinding
import com.example.retrofitwithflow.ui.main.MainViewModel
import com.example.retrofitwithflow.ui.main.NetworkResultMainViewModel
import com.example.retrofitwithflow.ui.main.TodoItemAdapter
import kotlinx.coroutines.flow.collect

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var todoItemAdapter: TodoItemAdapter

    //TodoResponse viewModel
//    private lateinit var viewModel: MainViewModel

    //TodoResponse viewModel
    private lateinit var viewModel: NetworkResultMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        binding.progressBar.isVisible = true

        //TodoResponse viewModel
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //NetworkResult viewModel
        viewModel = ViewModelProvider(this).get(NetworkResultMainViewModel::class.java)

        viewModel.getTodos()
        binding.progressBar.isVisible = true

        //using collect on TodoResonse instead of livedata observation
//        lifecycleScope.launchWhenStarted {
//            viewModel.myFlow.collect {
//                it?.let {
//                    todoItemAdapter.todos = it
//                }
//                binding.progressBar.isVisible = false
//            }
//        }
        subscribeObserver()
    }

    private fun setupRecyclerView() = binding.rvTodos.apply {
        todoItemAdapter = TodoItemAdapter()
        adapter = todoItemAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }


    private fun subscribeObserver() {

        //observe on TodoResponse
        viewModel.todos.observe(this) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    result.data?.let {
                        todoItemAdapter.todos = it
                    }
                    binding.progressBar.isVisible = false
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        this,
                        result.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar.isVisible = false
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        }
    }
}