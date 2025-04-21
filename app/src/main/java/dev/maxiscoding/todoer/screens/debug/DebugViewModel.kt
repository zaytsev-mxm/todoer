package dev.maxiscoding.todoer.screens.debug

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

const val TAG = "DebugViewModel"

@HiltViewModel
class DebugViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    companion object {
        private const val KEY_UI_STATE = "ui_state"
    }

    init {
        Log.d(TAG, "init")
    }

    private val _uiState = MutableStateFlow(
        savedStateHandle[KEY_UI_STATE] ?: DebugState()
    )
    val uiState: StateFlow<DebugState> = _uiState.asStateFlow()

    private fun updateState(reducer: (DebugState) -> DebugState) {
        _uiState.update { current ->
            val updated = reducer(current)
            savedStateHandle[KEY_UI_STATE] = updated
            updated
        }
    }

    fun changeName(newName: String) = updateState { it.copy(name = newName) }
}