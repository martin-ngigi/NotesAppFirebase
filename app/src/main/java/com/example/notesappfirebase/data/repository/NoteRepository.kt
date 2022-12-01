package com.example.notesappfirebase.data.repository

import android.net.Uri
import com.example.notesappfirebase.data.models.Note
import com.example.notesappfirebase.util.UiState

interface NoteRepository {
    //get notes from firebase

    //fun getNotes(user: User?, result: (UiState<List<Note>>) -> Unit)
    fun addNote(note: Note, result: (UiState<Pair<Note,String>>) -> Unit)
    fun getNotes(result: (UiState<List<Note>>) -> Unit)
    fun updateNote(note: Note, result: (UiState<String>) -> Unit)
    fun deleteNote(note: Note, result: (UiState<String>) -> Unit)
    suspend fun uploadSingleFile(fileUri: Uri, onResult: (UiState<Uri>) -> Unit)
    suspend fun uploadMultipleFile(fileUri: List<Uri>, onResult: (UiState<List<Uri>>) -> Unit)
}