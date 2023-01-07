package com.example.notesappfirebase.data.repository

import com.example.notesappfirebase.data.models.Task
import com.example.notesappfirebase.util.UiState

interface TaskRepository {
    fun addTask(task: Task, result: (UiState<Pair<Task, String>>) -> Unit)

}