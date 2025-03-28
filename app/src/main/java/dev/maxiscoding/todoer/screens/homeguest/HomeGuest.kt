package dev.maxiscoding.todoer.screens.homeguest

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
    onLoggedIn: () -> Unit
) {
    val homeGuestViewModel: HomeGuestViewModel = hiltViewModel()
    val viewModel = LocalAppViewModel.current
    val uiState = viewModel.uiState
    val isLoggedIn = uiState.isLoggedIn
    val isLoading = uiState.isLoading

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
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Home Screen Guest")
        Spacer(modifier = Modifier.height(16.dp))
        when {
            homeGuestUiState.wantsToRegister -> RegisterView(
                onRegister = { email, password ->
                    viewModel.registerUserViaEmail(email, password, email) { success ->
                        val msg = if (success) "Logged in successfully" else "Login failed"
                        Toast.makeText(myContext, msg, Toast.LENGTH_LONG).show()
                    }
                },
                onLogin = { homeGuestViewModel.toggleWantsToRegister() },
                isLoading = isLoading
            )

            else -> LoginView(
                onLogin = { login, password ->
                    viewModel.loginUserViaEmail(login, password) { success ->
                        val msg = if (success) "Logged in successfully" else "Login failed"
                        Toast.makeText(myContext, msg, Toast.LENGTH_LONG).show()
                    }
                },
                onRegister = { homeGuestViewModel.toggleWantsToRegister() },
                isLoading = isLoading
            )
        }
    }
}