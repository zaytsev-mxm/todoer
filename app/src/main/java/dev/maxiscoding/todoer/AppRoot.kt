package dev.maxiscoding.todoer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.maxiscoding.todoer.screens.HomeGuest
import dev.maxiscoding.todoer.screens.HomeAuthorised
import dev.maxiscoding.todoer.vms.AppRootViewModel

sealed class Screen(val route: String) {
    data object HomeGuest : Screen("HomeGuest")
    data object HomeAuthorised : Screen("HomeAuthorised")
}

@Composable
fun AppRoot(viewModel: AppRootViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.HomeGuest.route) {
        composable(Screen.HomeGuest.route) {
            HomeGuest(
                onLogin = { email, password -> viewModel.registerUserViaEmail(email, password) },
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(Screen.HomeAuthorised.route) {
            HomeAuthorised()
        }
    }
}