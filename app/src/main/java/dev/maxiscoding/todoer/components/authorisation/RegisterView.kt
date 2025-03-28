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
fun RegisterView(
    vm: HomeGuestViewModel = hiltViewModel(),
    onRegister: (String, String) -> Unit,
    isLoading: Boolean
) {
    val uiState = vm.uiState
    val registerForm = uiState.registerForm
    val email = registerForm.email
    val password = registerForm.password

    Column {
        Text("Register")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { vm.updateRegisterForm(registerForm.copy(email = it)) },
            label = { Text("Email") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { vm.updateRegisterForm(registerForm.copy(password = it)) },
            label = { Text("Password") }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { onRegister(email, password) }, enabled = !isLoading) {
            Text("Register")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Already have an account? Login",
            modifier = Modifier.clickable { vm.toggleWantsToRegister() })
    }
}