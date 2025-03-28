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
    val isLoggedIn by viewModel.uiState::isLoggedIn

    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            navController.navigate(Screen.HomeGuest.route)
        }
    }

    NavHost(navController = navController, startDestination = Screen.HomeGuest.route) {
        composable(Screen.HomeGuest.route) {
            HomeGuest(navController)
        }
        composable(Screen.HomeAuthorised.route) {
            HomeAuthorised()
        }
    }
}