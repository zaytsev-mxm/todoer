package dev.maxiscoding.todoer.vms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.maxiscoding.todoer.model.RegisterRequest
import dev.maxiscoding.todoer.services.apiService
import kotlinx.coroutines.launch

data class AppState(
    var isLoading: Boolean = false,
    val token: String? = null,
    val error: String? = null
)

class AppRootViewModel : ViewModel() {
    private var _appState by mutableStateOf(AppState())
    val appState: AppState
        get() = _appState

    fun registerUserViaEmail(email: String, password: String) {
        _appState = _appState.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val request = RegisterRequest(email.trim(), login = email.trim(), password.trim())
                val response = apiService.registerUserViaEmail(request)

                if (response.isSuccessful) {
                    println("Success")
                    println(response)
                    _appState = _appState.copy(
                        isLoading = false,
                        token = response.body()?.token
                    )
                } else {
                    val msg = "Error with code: ${response.code()}"
                    println(msg)
                    _appState = _appState.copy(
                        isLoading = false,
                        error = msg
                    )
                }
            } catch (e: Exception) {
                println("Error unknown: ${e.message}")
                println(e.message)
                _appState = _appState.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}