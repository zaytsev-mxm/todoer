package dev.maxiscoding.todoer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.maxiscoding.todoer.screens.HomeGuest
import dev.maxiscoding.todoer.screens.HomeAuthorised

sealed class Screen(val route: String) {
    data object HomeGuest : Screen("HomeGuest")
    data object HomeAuthorised : Screen("HomeAuthorised")
}

@Composable
fun AppRoot(viewModel: AppRootViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val uiState = viewModel.uiState
    val isLoggedIn by uiState::isLoggedIn
    val token by uiState::token

    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            navController.navigate(Screen.HomeGuest.route)
        }
    }

    NavHost(navController = navController, startDestination = Screen.HomeGuest.route) {
        composable(Screen.HomeGuest.route) {
            HomeGuest(
                isLoggedIn = isLoggedIn,
                isLoading = uiState.isLoading,
                onLoggedIn = { navController.navigate(Screen.HomeAuthorised.route) },
                onLogin = viewModel::loginUserViaEmail,
                onRegister = viewModel::registerUserViaEmail
            )
        }
        composable(Screen.HomeAuthorised.route) {
            HomeAuthorised(token = token, onLogout = viewModel::logout)
        }
    }
}