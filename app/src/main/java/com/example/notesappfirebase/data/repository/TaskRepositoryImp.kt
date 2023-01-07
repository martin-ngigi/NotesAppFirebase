package com.example.notesappfirebase.data.repository

import com.example.notesappfirebase.data.models.Task
import com.example.notesappfirebase.util.FireDatabase
import com.example.notesappfirebase.util.UiState
import com.google.firebase.database.FirebaseDatabase

class TaskRepositoryImp (
    val database: FirebaseDatabase
    ): TaskRepository{

    override fun addTask(task: Task, result: (UiState<Pair<Task, String>>) -> Unit) {
        val reference = database.reference.child(FireDatabase.TASK).push()
        val uniqueKey = reference.key?: "invalid" // if key is empty, "invalid" will be set
        task.id = uniqueKey
        reference
            .setValue(task)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success(Pair(task, "Task has been created successfully"))
                )
            }
            .addOnFailureListener{
                result.invoke(
                    UiState.Failure( it.localizedMessage )
                )
            }
    }
}