package com.example.notesappfirebase.data.models

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize //pass data btwn fragments
data class Note(
    var id: String = "",
    val text: String = "",
    @ServerTimestamp
    val date: Date = Date(),
): Parcelable