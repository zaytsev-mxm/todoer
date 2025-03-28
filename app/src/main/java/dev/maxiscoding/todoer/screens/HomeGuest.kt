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
import dev.maxiscoding.todoer.components.authorisation.LoginView
import dev.maxiscoding.todoer.components.authorisation.RegisterView

@Composable
fun HomeGuest(
    isLoggedIn: Boolean,
    isLoading: Boolean,
    onLoggedIn: () -> Unit,
    onLogin: (login: String, password: String, cb: (success: Boolean) -> Unit) -> Unit,
    onRegister: (email: String, password: String, login: String?, cb: (success: Boolean) -> Unit) -> Unit,
) {
    var wantsToRegister by rememberSaveable { mutableStateOf(false) }

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
            wantsToRegister -> RegisterView(
                onRegister = { email, password ->
                    onRegister(email, password, email) { success ->
                        val msg = if (success) "Logged in successfully" else "Login failed"
                        Toast.makeText(myContext, msg, Toast.LENGTH_LONG).show()
                    }
                },
                onLogin = { wantsToRegister = false },
                isLoading = isLoading
            )

            else -> LoginView(
                onLogin = { login, password ->
                    onLogin(login, password) { success ->
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