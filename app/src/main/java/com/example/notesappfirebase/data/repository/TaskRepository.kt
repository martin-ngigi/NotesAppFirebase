package com.example.notesappfirebase.data.repository

import com.example.notesappfirebase.data.models.Task
import com.example.notesappfirebase.data.models.User
import com.example.notesappfirebase.util.UiState

interface TaskRepository {
    fun addTask(task: Task, result: (UiState<Pair<Task,String>>) -> Unit)
    fun getTasks(user: User?, result: (UiState<List<Task>>) -> Unit)
    fun updateTask(task: Task, result: (UiState<Pair<Task,String>>) -> Unit)
    fun deleteTask(task: Task, result: (UiState<Pair<Task,String>>) -> Unit)

}