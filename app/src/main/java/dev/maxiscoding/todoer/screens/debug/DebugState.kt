package dev.maxiscoding.todoer.screens.debug

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DebugState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val name: String? = null
): Parcelable