package dev.maxiscoding.todoer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.maxiscoding.todoer.screens.homeguest.HomeGuest
import dev.maxiscoding.todoer.screens.homeauth.HomeAuthorised

sealed class Screen(val route: String) {
    data object HomeGuest : Screen("HomeGuest")
    data object HomeAuthorised : Screen("HomeAuthorised")
}

@Composable
fun AppRoot() {
    val viewModel: AppViewModel = hiltViewModel()

    val navController = rememberNavController()
    val uiState = viewModel.uiState
    val isLoggedIn by uiState::isLoggedIn

    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            navController.navigate(Screen.HomeGuest.route)
        }
    }


    CompositionLocalProvider(
        LocalAppViewModel provides viewModel
    ) {
        NavHost(navController = navController, startDestination = Screen.HomeGuest.route) {
            composable(Screen.HomeGuest.route) {
                HomeGuest(
                    onLoggedIn = { navController.navigate(Screen.HomeAuthorised.route) },
                )
            }
            composable(Screen.HomeAuthorised.route) {
                HomeAuthorised()
            }
        }
    }
}