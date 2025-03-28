package dev.maxiscoding.todoer.screens.homeguest

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class LoginForm(
    val email: String = "",
    val login: String = "",
    val password: String = "",
)

data class RegisterForm(
    val email: String = "",
    val login: String = "",
    val password: String = "",
)

data class HomeGuestState(
    val wantsToRegister: Boolean = false,
    val loginForm: LoginForm = LoginForm(),
    val registerForm: RegisterForm = RegisterForm(),
)

@HiltViewModel
class HomeGuestViewModel @Inject constructor() : ViewModel() {
    var uiState by mutableStateOf(HomeGuestState())
        private set

    fun toggleWantsToRegister() {
        uiState = uiState.copy(wantsToRegister = !uiState.wantsToRegister)
    }

    fun updateLoginForm(loginForm: LoginForm) {
        uiState = uiState.copy(loginForm = loginForm)
    }

    fun updateRegisterForm(registerForm: RegisterForm) {
        uiState = uiState.copy(registerForm = registerForm)
    }
}