package dev.maxiscoding.todoer.screens.debug

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


data class DebugState(
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class DebugViewModel @Inject constructor() : ViewModel() {
    var uiState by mutableStateOf(DebugState())
        private set

    fun toggleIsLoading() {
        uiState = uiState.copy(isLoading = !uiState.isLoading)
    }
}