package dev.maxiscoding.todoer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppState(
    var isLoading: Boolean = false,
    val token: String? = null,
    val error: String? = null
): Parcelable