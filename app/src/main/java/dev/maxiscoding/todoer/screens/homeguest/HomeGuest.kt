package dev.maxiscoding.todoer.screens.homeguest

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.maxiscoding.todoer.components.authorisation.LoginView
import dev.maxiscoding.todoer.components.authorisation.RegisterView
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.maxiscoding.todoer.Screen

const val TAG = "HomeGuest"

@Composable
fun HomeGuest(
    viewModel: HomeGuestViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().padding(horizontal = 48.dp)
    ) {
        Text("Home Screen Guest", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(48.dp))
        when {
            uiState.wantsToRegister -> RegisterView(
                onRegister = { email, password ->
                    Log.d(TAG, "Registering user with email: $email and password: $password")
                },
                isLoading = false
            )

            else -> LoginView(
                onLogin = { navController.navigate(Screen.HomeAuthorised.route) },
                isLoading = false
            )
        }
    }
}