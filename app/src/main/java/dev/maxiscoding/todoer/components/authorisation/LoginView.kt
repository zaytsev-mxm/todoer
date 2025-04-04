package dev.maxiscoding.todoer.components.authorisation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.maxiscoding.todoer.screens.homeguest.HomeGuestViewModel

@Composable
fun LoginView(
    vm: HomeGuestViewModel = hiltViewModel(),
    onLogin: () -> Unit,
    isLoading: Boolean
) {
    val uiState = vm.uiState
    val loginForm = uiState.loginForm
    val login = loginForm.login
    val password = loginForm.password

    Column {
        Text("Login")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = login,
            onValueChange = { vm.updateLoginForm(loginForm.copy(login = it)) },
            label = { Text("Login or email") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { vm.updateLoginForm(loginForm.copy(password = it)) },
            label = { Text("Password") }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { onLogin() },
            enabled = !isLoading
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Do not have an account? Register",
            modifier = Modifier.clickable { vm.toggleWantsToRegister() })
    }
}