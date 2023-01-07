package com.example.notesappfirebase.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    var id: String="",
    var user_id: String="",
    var description: String="",
    var date: String="",
): Parcelable
