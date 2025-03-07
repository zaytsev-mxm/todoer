package dev.maxiscoding.todoer.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeGuest(
    onLogin: (String, String) -> Unit,
    onRegister: (String, String) -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var wantsToRegister by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text("Home Screen Guest")
        Spacer(modifier = Modifier.height(16.dp))
        when {
            wantsToRegister -> Column {
                Text("Register")
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") }
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") }
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = { onRegister(email, password) }, enabled = !isLoading) {
                    Text("Register")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Already have an account? Login",
                    modifier = Modifier.clickable { wantsToRegister = false })
            }

            else -> Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text("Login")
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") }
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") }
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { onLogin(email, password) },
                    enabled = !isLoading
                ) {
                    Text("Login")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Do not have an account? Register",
                    modifier = Modifier.clickable { wantsToRegister = true })
            }
        }
    }
}