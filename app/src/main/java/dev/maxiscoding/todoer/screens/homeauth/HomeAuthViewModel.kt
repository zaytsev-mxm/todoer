package dev.maxiscoding.todoer.screens.homeauth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxiscoding.todoer.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeAuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    companion object {
        private const val KEY_UI_STATE = "ui_state_home_auth_view_model"
    }

    init {
        viewModelScope.launch {
            authRepository.observeTokenFlow().collect { token ->
                updateState { it.copy(token = token) }
            }
        }
    }

    private val _uiState = MutableStateFlow(
        savedStateHandle[KEY_UI_STATE] ?: HomeAuthState()
    )
    val uiState: StateFlow<HomeAuthState> = _uiState.asStateFlow()

    private fun updateState(reducer: (HomeAuthState) -> HomeAuthState) {
        _uiState.update { current ->
            val updated = reducer(current)
            savedStateHandle[KEY_UI_STATE] = updated
            updated
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.setToken(null)
        }
    }
}