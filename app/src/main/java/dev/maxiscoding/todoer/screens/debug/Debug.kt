package dev.maxiscoding.todoer.screens.debug

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Debug(viewModel: DebugViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState
    val text = if (uiState.isLoading) "Loading..." else "Not loading"

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = viewModel::toggleIsLoading) {
            Text("Toggle loading")
        }
    }
}