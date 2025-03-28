package dev.maxiscoding.todoer.screens.homeauth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class HomeAuthState(
    val myName: String = ""
)

@HiltViewModel
class HomeAuthViewModel @Inject constructor() : ViewModel() {

}