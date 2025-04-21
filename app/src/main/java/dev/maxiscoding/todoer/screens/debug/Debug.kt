package dev.maxiscoding.todoer.screens.debug

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Debug(viewModel: DebugViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Hello, choovyrlo! Is your name really ${uiState.name ?: "NO_NAME"}")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = uiState.name ?: "", onValueChange = {
            viewModel.changeName(it)
        })
    }
}