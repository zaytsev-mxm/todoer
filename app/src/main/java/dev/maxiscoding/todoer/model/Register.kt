package dev.maxiscoding.todoer.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Register(
    val token: String,
): Parcelable

data class RegisterRequest(val email: String, val login: String, val password: String)