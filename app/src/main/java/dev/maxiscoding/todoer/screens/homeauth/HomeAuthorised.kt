package dev.maxiscoding.todoer.screens.homeauth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.maxiscoding.todoer.LocalAppViewModel

@Composable
fun HomeAuthorised() {
    val homeAuthViewModel: HomeAuthViewModel = hiltViewModel()
    val viewModel = LocalAppViewModel.current
    val token by viewModel.uiState::token
    val onLogout = viewModel::logout

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home Screen Authorised")
        Box {

            val user by remember { derivedStateOf { viewModel.uiState } }

            Text(text = "Hello, ${user.token}!")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text("Token is $token")
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { onLogout.invoke() }) {
            Text("Logout")
        }
    }
}