package com.example.notesappfirebase.presentation.di

import com.example.notesappfirebase.data.repository.NoteRepository
import com.example.notesappfirebase.data.repository.NoteRepositoryImp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
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
}