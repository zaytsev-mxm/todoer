package dev.maxiscoding.todoer.screens.homeauth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.maxiscoding.todoer.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeAuthState(
    val myName: String = ""
)

@HiltViewModel
class HomeAuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    var uiState by mutableStateOf(HomeAuthState())

    init {
        viewModelScope.launch {
            authRepository.tokenFlow.collect { token ->
                val name = "_${token}_"
                uiState = uiState.copy(myName = name)
            }
        }
    }
}