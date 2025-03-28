package dev.maxiscoding.todoer.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.maxiscoding.todoer.Screen
import dev.maxiscoding.todoer.components.authorisation.LoginView
import dev.maxiscoding.todoer.components.authorisation.RegisterView
import dev.maxiscoding.todoer.AppRootViewModel
import dev.maxiscoding.todoer.isLoggedIn

@Composable
fun HomeGuest(
    navController: NavController,
    viewModel: AppRootViewModel
) {
    var wantsToRegister by rememberSaveable { mutableStateOf(false) }

    val myContext = LocalContext.current

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
            wantsToRegister -> RegisterView(
                onRegister = { email, password ->
                    viewModel.registerUserViaEmail(email, password) { success ->
                        val msg = if (success) "Logged in successfully" else "Login failed"
                        Toast.makeText(myContext, msg, Toast.LENGTH_LONG).show()
                    }
                },
                onLogin = { wantsToRegister = false },
                isLoading = isLoading
            )

            else -> LoginView(
                onLogin = { login, password ->
                    viewModel.loginUserViaEmail(login, password) { success ->
                        val msg = if (success) "Logged in successfully" else "Login failed"
                        Toast.makeText(myContext, msg, Toast.LENGTH_LONG).show()
                    }
                },
                onRegister = { wantsToRegister = true },
                isLoading = isLoading
            )
        }
    }
}