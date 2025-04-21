package dev.maxiscoding.todoer.screens.homeguest

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxiscoding.todoer.model.LoginRequest
import dev.maxiscoding.todoer.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeGuestViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val authRepository: AuthRepository
) : ViewModel() {
    companion object {
        private const val KEY_UI_STATE = "ui_state_home_guest_view_model"
    }

    private val _uiState = MutableStateFlow(
        savedStateHandle[KEY_UI_STATE] ?: HomeGuestState()
    )
    val uiState: StateFlow<HomeGuestState> = _uiState.asStateFlow()

    private fun updateState(reducer: (HomeGuestState) -> HomeGuestState) {
        _uiState.update { current ->
            val updated = reducer(current)
            savedStateHandle[KEY_UI_STATE] = updated
            updated
        }
    }

    fun toggleWantsToRegister() {
        updateState({ it.copy(wantsToRegister = !it.wantsToRegister) })
    }

    fun updateLoginForm(loginForm: LoginForm) {
        updateState({ it.copy(loginForm = loginForm) })
    }

    fun updateRegisterForm(registerForm: RegisterForm) {
        updateState({ it.copy(registerForm = registerForm) })
    }

    fun loginUserViaEmail(onFinish: (e: Exception?) -> Unit = {}) {
        val request = LoginRequest(uiState.value.loginForm.login, uiState.value.loginForm.password)
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