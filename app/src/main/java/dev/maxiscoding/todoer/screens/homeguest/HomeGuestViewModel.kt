package dev.maxiscoding.todoer.screens.homeguest

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxiscoding.todoer.model.LoginRequest
import dev.maxiscoding.todoer.repository.AuthRepository
import kotlinx.coroutines.launch
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
class HomeGuestViewModel @Inject constructor(
    val authRepository: AuthRepository
) : ViewModel() {
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

    fun loginUserViaEmail(onFinish: (e: Exception?) -> Unit = {}) {
        val request = LoginRequest(uiState.loginForm.login, uiState.loginForm.password)
        viewModelScope.launch {
            val response = authRepository.loginUserViaEmail(request)

            if (response.isSuccessful) {
                println("Success")
                println(response)
                val token = response.body()?.token
                authRepository.setToken(token)
                onFinish(null)
            } else {
                val msg = "Error with code: ${response.code()}"
                println(msg)
                authRepository.setToken(null)
                onFinish(Exception("web error: $msg"))
            }
        }
    }
}