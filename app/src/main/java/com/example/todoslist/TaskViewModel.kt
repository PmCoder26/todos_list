package com.example.todoslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class TaskViewModel(
    private val taskDao: TaskDao
) : ViewModel() {

    private val tasks = taskDao.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = tasks

    fun addTask(task: Task){
        viewModelScope.launch {
            try {
                taskDao.addTask(task)
            } catch (e: Exception){
                println(e.localizedMessage)
            }
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch {
            try{
                taskDao.removeTask(task)
            } catch(e: Exception){
                println(e.localizedMessage)
            }
        }
    }



}