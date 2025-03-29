package dev.maxiscoding.todoer.screens.homeguest

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.maxiscoding.todoer.components.authorisation.LoginView
import dev.maxiscoding.todoer.components.authorisation.RegisterView
import dev.maxiscoding.todoer.LocalAppViewModel
import dev.maxiscoding.todoer.isLoggedIn

@Composable
fun HomeGuest(
    homeGuestViewModel: HomeGuestViewModel = hiltViewModel(),
    onLoggedIn: () -> Unit
) {
    val vm = LocalAppViewModel.current
    val uiState = vm.uiState
    val isLoggedIn by uiState::isLoggedIn
    val isLoading by uiState::isLoading

    val homeGuestUiState = homeGuestViewModel.uiState

    val myContext = LocalContext.current

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            onLoggedIn.invoke()
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(horizontal = 48.dp)
    ) {
        Text("Home Screen Guest", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(48.dp))
        when {
            homeGuestUiState.wantsToRegister -> RegisterView(
                vm = homeGuestViewModel,
                onRegister = { email, password ->
                    vm.registerUserViaEmail(email, password, email) { error ->
                        val msg = if (error == null) "Logged in successfully" else "Login failed: ${error.message}"
                        Toast.makeText(myContext, msg, Toast.LENGTH_LONG).show()
                    }
                },
                isLoading = isLoading
            )

            else -> LoginView(
                vm = homeGuestViewModel,
                onLogin = { login, password ->
                    vm.loginUserViaEmail(login, password) { error ->
                        val msg = if (error == null) "Logged in successfully" else "Login failed: ${error.message}"
                        Toast.makeText(myContext, msg, Toast.LENGTH_LONG).show()
                    }
                },
                isLoading = isLoading
            )
        }
    }
}