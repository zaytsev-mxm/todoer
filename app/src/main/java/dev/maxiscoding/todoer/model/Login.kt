package dev.maxiscoding.todoer.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Login(
    val token: String,
): Parcelable

data class LoginRequest(val login: String, val password: String)