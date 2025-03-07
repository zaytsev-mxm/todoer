package dev.maxiscoding.todoer.vms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.maxiscoding.todoer.model.LoginRequest
import dev.maxiscoding.todoer.model.RegisterRequest
import dev.maxiscoding.todoer.services.apiService
import kotlinx.coroutines.launch

data class AppState(
    var isLoading: Boolean = false,
    val token: String? = null,
    val error: String? = null
)

val AppState.isLoggedIn: Boolean get() = token != null && error == null

class AppRootViewModel : ViewModel() {
    var uiState by mutableStateOf(AppState())
        private set

    fun registerUserViaEmail(email: String, password: String, login: String? = null) {
        uiState = uiState.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val request = RegisterRequest(
                    email = email.trim(),
                    login = login ?: email.trim(),
                    password = password.trim()
                )
                val response = apiService.registerUserViaEmail(request)

                if (response.isSuccessful) {
                    println("Success")
                    println(response)
                    uiState = uiState.copy(
                        isLoading = false,
                        token = response.body()?.token
                    )
                } else {
                    val msg = "Error with code: ${response.code()}"
                    println(msg)
                    uiState = uiState.copy(
                        isLoading = false,
                        error = msg
                    )
                }
            } catch (e: Exception) {
                println("Error unknown: ${e.message}")
                println(e.message)
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun loginUserViaEmail(login: String, password: String, onFinish: (success: Boolean) -> Unit) {
        uiState = uiState.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val request = LoginRequest(login.trim(), password.trim())
                val response = apiService.loginUserViaEmail(request)

                if (response.isSuccessful) {
                    println("Success")
                    println(response)
                    uiState = uiState.copy(
                        isLoading = false,
                        token = response.body()?.token
                    )
                    onFinish(true)
                } else {
                    val msg = "Error with code: ${response.code()}"
                    println(msg)
                    uiState = uiState.copy(
                        isLoading = false,
                        error = msg
                    )
                    onFinish(false)
                }
            } catch (e: Exception) {
                println("Error unknown: ${e.message}")
                println(e.message)
                uiState = uiState.copy(
                    isLoading = false,
                    error = e.message
                )
                onFinish(false)
            }
        }
    }
}