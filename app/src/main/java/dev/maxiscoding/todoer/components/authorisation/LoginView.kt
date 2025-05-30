package dev.maxiscoding.todoer.components.authorisation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.maxiscoding.todoer.screens.homeguest.HomeGuestViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import dev.maxiscoding.todoer.TAG

const val TAG_LOGIN_VIEW = "LoginView"

@Composable
fun LoginView(
    viewModel: HomeGuestViewModel = hiltViewModel(),
    onLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val loginForm = uiState.loginForm
    val login = loginForm.login
    val password = loginForm.password

    Column {
        Text("Login")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = login,
            onValueChange = { viewModel.updateLoginForm(loginForm.copy(login = it)) },
            label = { Text("Login or email") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                showKeyboardOnFocus = true,
            )
        )
        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.updateLoginForm(loginForm.copy(password = it)) },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                showKeyboardOnFocus = true,
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                viewModel.loginUserViaEmail { error ->
                    if (error == null) {
                        Log.d(TAG, "Logged in successfully")
                        onLogin()
                    } else {
                        Log.e(TAG, "Error logging in: ${error.message}")
                    }
                }
            },
            enabled = !uiState.isLoading
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Do not have an account? Register",
            modifier = Modifier.clickable { viewModel.toggleWantsToRegister() })
    }
}