package dev.maxiscoding.todoer.screens.homeauth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.maxiscoding.todoer.LocalAppViewModel

@Composable
fun HomeAuthorised(homeAuthViewModel: HomeAuthViewModel = hiltViewModel()) {
    val onLogout = LocalAppViewModel.current::logout

    val uiState = homeAuthViewModel.uiState

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home Screen Authorised", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(48.dp))
        Text("Hello, ${uiState.token}!")
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { onLogout.invoke() }) {
            Text("Logout")
        }
    }
}