package dev.maxiscoding.todoer

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxiscoding.todoer.model.RegisterRequest
import dev.maxiscoding.todoer.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    companion object {
        private const val KEY_UI_STATE = "ui_state_app_view_model"
    }

    private val _uiState = MutableStateFlow(
        savedStateHandle[KEY_UI_STATE] ?: AppState()
    )
    val uiState: StateFlow<AppState> = _uiState.asStateFlow()

    private fun updateState(reducer: (AppState) -> AppState) {
        _uiState.update { current ->
            val updated = reducer(current)
            savedStateHandle[KEY_UI_STATE] = updated
            updated
        }
    }

    init {
        viewModelScope.launch {
            authRepository.observeTokenFlow()
                .distinctUntilChanged()
                .collect { newToken ->
                    Log.d(TAG, "Token is: $newToken")
                    setToken(newToken)
                    updateState { it.copy(hasInitialDataReceived = true) }
                }
        }
    }

    fun registerUserViaEmail(
        email: String,
        password: String,
        login: String? = null,
        onFinish: (e: Exception?) -> Unit = {}
    ) {
        updateState { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val request = RegisterRequest(
                    email = email.trim(),
                    login = login ?: email.trim(),
                    password = password.trim()
                )
                val response = authRepository.registerUserViaEmail(request)

                if (response.isSuccessful) {
                    println("Success")
                    println(response)
                    val token = response.body()?.token
                    updateState { it.copy(isLoading = false, token = token) }
                    setToken(token = token)
                    onFinish(null)
                } else {
                    val msg = "Error with code: ${response.code()}"
                    println(msg)
                    updateState { it.copy(isLoading = false, error = msg) }
                    onFinish(Exception("web error: $msg"))
                }
            } catch (e: Exception) {
                println("Error unknown: ${e.message}")
                println(e.message)
                updateState { it.copy(isLoading = false, error = e.message) }
                onFinish(e)
            }
        }
    }

    suspend fun setToken(token: String?) {
        updateState { it.copy(token = token) }
        authRepository.setToken(token = token)
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.setToken(null)
        }
    }
}