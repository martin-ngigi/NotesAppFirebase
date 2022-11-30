package com.example.notesappfirebase.data.repository

import com.example.notesappfirebase.data.models.Note
import com.example.notesappfirebase.util.UiState

interface NoteRepository {
    //get notes from firebase
    fun getNotes(result: (UiState<List<Note>>) -> Unit)
    fun addNote(note: Note, result: (UiState<String>) -> Unit)
    fun updateNote(note: Note, result: (UiState<String>) -> Unit)
    fun deleteNote(note: Note, result: (UiState<String>) -> Unit)
}