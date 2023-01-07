package com.example.notesappfirebase.presentation.di

import android.content.SharedPreferences
import com.example.notesappfirebase.data.repository.AuthRepository
import com.example.notesappfirebase.data.repository.AuthRepositoryImp
import com.example.notesappfirebase.data.repository.NoteRepository
import com.example.notesappfirebase.data.repository.NoteRepositoryImp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNoteRepository(
        database: FirebaseFirestore,
        storageReference: StorageReference
    ): NoteRepository{
        return NoteRepositoryImp(database,storageReference)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth,
        appPreferences: SharedPreferences,
        gson: Gson
    ): AuthRepository{
        return AuthRepositoryImp(auth, database, appPreferences, gson)
    }
}