package dev.maxiscoding.todoer.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Token(
    val token: String,
): Parcelable

@Parcelize
data class TokenResponse(val token: String): Parcelable