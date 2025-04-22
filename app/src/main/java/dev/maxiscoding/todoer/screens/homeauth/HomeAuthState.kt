package dev.maxiscoding.todoer.screens.homeauth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HomeAuthState(
    val token: String? = null
): Parcelable