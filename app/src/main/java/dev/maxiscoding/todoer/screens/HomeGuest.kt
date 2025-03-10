package dev.maxiscoding.todoer.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.maxiscoding.todoer.Screen
import dev.maxiscoding.todoer.vms.AppRootViewModel
import dev.maxiscoding.todoer.vms.isLoggedIn

@Composable
fun HomeGuest(
    navController: NavController,
    viewModel: AppRootViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var wantsToRegister by remember { mutableStateOf(false) }

    val isLoggedIn by viewModel.uiState::isLoggedIn
    val isLoading = viewModel.uiState.isLoading

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate(Screen.HomeAuthorised.route)
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
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
                Button(onClick = {
                    viewModel.registerUserViaEmail(
                        email,
                        password
                    )
                }, enabled = !isLoading) {
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
                    onClick = {
                        viewModel.loginUserViaEmail(
                            email,
                            password,
                        )
                    },
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