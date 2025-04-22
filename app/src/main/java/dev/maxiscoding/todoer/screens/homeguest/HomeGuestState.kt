package dev.maxiscoding.todoer.screens.homeguest

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginForm(
    val email: String = "",
    val login: String = "",
    val password: String = "",
): Parcelable

@Parcelize
data class RegisterForm(
    val email: String = "",
    val login: String = "",
    val password: String = "",
): Parcelable

@Parcelize
data class HomeGuestState(
    val isLoading: Boolean = false,
    val wantsToRegister: Boolean = false,
    val loginForm: LoginForm = LoginForm(),
    val registerForm: RegisterForm = RegisterForm(),
): Parcelable