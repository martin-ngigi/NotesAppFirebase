package com.example.notesappfirebase.data.repository

import com.example.notesappfirebase.data.models.User
import com.example.notesappfirebase.util.UiState

interface AuthRepository {
    fun registerUser(email: String, password: String, user: User, result: (UiState<String>) -> Unit)
    fun updateUserInfo(user: User, result: (UiState<String>) -> Unit)
    fun storeSession(id: String, result: (User?) -> Unit)
    fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit)
    fun getSession(result: (User?) -> Unit)
    fun logout(result: () -> Unit)
    fun forgotPassword(email: String, result: (UiState<String>) -> Unit)
}