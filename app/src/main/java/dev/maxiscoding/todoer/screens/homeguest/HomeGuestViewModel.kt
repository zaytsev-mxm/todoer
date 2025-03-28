package dev.maxiscoding.todoer.screens.homeguest

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class HomeGuestState(
    val wantsToRegister: Boolean = false,
)

@HiltViewModel
class HomeGuestViewModel @Inject constructor() : ViewModel() {
    var uiState by mutableStateOf(HomeGuestState())
        private set

    fun toggleWantsToRegister() {
        uiState = uiState.copy(wantsToRegister = !uiState.wantsToRegister)
    }
}