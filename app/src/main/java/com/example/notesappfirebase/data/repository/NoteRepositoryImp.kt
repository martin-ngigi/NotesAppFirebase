package com.example.notesappfirebase.data.repository

import android.net.Uri
import com.example.notesappfirebase.data.models.Note
import com.example.notesappfirebase.util.FireStoreCollection
import com.example.notesappfirebase.util.FirebaseStorageConstants.NOTE_IMAGES
import com.example.notesappfirebase.util.UiState
import com.google.firebase.FirebaseException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class NoteRepositoryImp(
    val database: FirebaseFirestore,
    val storageReference: StorageReference
) : NoteRepository {

    override fun getNotes(result: (UiState<List<Note>>) -> Unit)  {
        database.collection(FireStoreCollection.NOTE)
            .get()
            .addOnSuccessListener {
                val notes = arrayListOf<Note>()
                for (document in it){
                    val note = document.toObject(Note::class.java)
                    notes.add(note)
                }
                result.invoke(
                    UiState.Success(notes)
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun addNote(note: Note, result:  (UiState<Pair<Note,String>>) -> Unit) {
        val document = database.collection(FireStoreCollection.NOTE).document()
        note.id = document.id
        document
            .set(note)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success(Pair(note,"Note has been created successfully"))

                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun updateNote(note: Note, result: (UiState<String>) -> Unit) {
        val document = database.collection(FireStoreCollection.NOTE).document(note.id)
        document
            .set(note)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Note has been update successfully")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun deleteNote(note: Note, result: (UiState<String>) -> Unit) {
        val document = database.collection(FireStoreCollection.NOTE).document(note.id)
        document
            .delete()
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Note has been deleted successfully")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    /**
    override suspend fun uploadSingleFile(fileUri: Uri, onResult: (UiState<Uri>) -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun uploadMultipleFile(
        fileUri: List<Uri>,
        onResult: (UiState<List<Uri>>) -> Unit
    ) {
        TODO("Not yet implemented")
    }
    **/
    override suspend fun uploadSingleFile(fileUri: Uri, onResult: (UiState<Uri>) -> Unit) {
        try {
            val uri: Uri = withContext(Dispatchers.IO) {
                storageReference
                    .putFile(fileUri)
                    .await()
                    .storage
                    .downloadUrl
                    .await()
            }
            onResult.invoke(UiState.Success(uri))
        } catch (e: FirebaseException){
            onResult.invoke(UiState.Failure(e.message))
        }catch (e: Exception){
            onResult.invoke(UiState.Failure(e.message))
        }
    }

    override suspend fun uploadMultipleFile(
        fileUri: List<Uri>,
        onResult: (UiState<List<Uri>>) -> Unit
    ) {
        try {
            val uri: List<Uri> = withContext(Dispatchers.IO) {
                fileUri.map { image ->
                    async {
                        storageReference.child(NOTE_IMAGES).child(image.lastPathSegment ?: "${System.currentTimeMillis()}")
                            .putFile(image)
                            .await()
                            .storage
                            .downloadUrl
                            .await()
                    }
                }.awaitAll()
            }
            onResult.invoke(UiState.Success(uri))
        } catch (e: FirebaseException){
            onResult.invoke(UiState.Failure(e.message))
        }catch (e: Exception){
            onResult.invoke(UiState.Failure(e.message))
        }
    }
}