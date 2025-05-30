package dev.maxiscoding.todoer.screens.homeguest

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.maxiscoding.todoer.model.LoginRequest
import dev.maxiscoding.todoer.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG_VM = "HomeGuestViewModel"

@HiltViewModel
class HomeGuestViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val authRepository: AuthRepository,
    @ApplicationContext private val context: android.content.Context,
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

        updateState({ it.copy(isLoading = true) })

        viewModelScope.launch {
            // Emulate a slow network connection
            // to see the loading state
            delay(2 * 1000)

            val response = authRepository.loginUserViaEmail(request)

            updateState({ it.copy(isLoading = false) })

            if (response.isSuccessful) {
                Log.i(TAG_VM, "Logged in successfully")
                Log.d(TAG_VM, response.body().toString())
                val token = response.body()?.token
                authRepository.setToken(token)
                onFinish(null)
                Toast.makeText(context, "Logged in successfully", Toast.LENGTH_SHORT).show()
            } else {
                val msg = "Error with code: ${response.code()}"
                Log.e(TAG_VM, msg)
                authRepository.setToken(null)
                onFinish(Exception("web error: $msg"))
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}