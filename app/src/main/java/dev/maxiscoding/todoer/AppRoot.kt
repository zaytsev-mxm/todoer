package dev.maxiscoding.todoer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.maxiscoding.todoer.screens.debug.Debug
import dev.maxiscoding.todoer.screens.debug.DebugViewModel
import dev.maxiscoding.todoer.screens.homeguest.HomeGuest
import dev.maxiscoding.todoer.screens.homeauth.HomeAuthorised
import dev.maxiscoding.todoer.screens.homeguest.HomeGuestViewModel

sealed class Screen(val route: String) {
    data object HomeGuest : Screen("HomeGuest")
    data object HomeAuthorised : Screen("HomeAuthorised")
    data object Debug : Screen("Debug")
}

val DefaultRoute = Screen.HomeGuest.route

@Composable
fun AppRoot() {
    val viewModel: AppViewModel = hiltViewModel()

    val homeGuestViewModel: HomeGuestViewModel = hiltViewModel()
    val debugViewModel: DebugViewModel = hiltViewModel()

    val navController = rememberNavController()
    val uiState = viewModel.uiState
    val isLoggedIn by uiState::isLoggedIn

    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            navController.navigate(DefaultRoute)
        }
    }


    CompositionLocalProvider(
        LocalAppViewModel provides viewModel
    ) {
        NavHost(navController = navController, startDestination = DefaultRoute) {
            composable(Screen.HomeGuest.route) {
                HomeGuest(homeGuestViewModel) {
                    navController.navigate(Screen.HomeAuthorised.route)
                }
            }
            composable(Screen.HomeAuthorised.route) {
                HomeAuthorised()
            }
            composable(Screen.Debug.route) {
                Debug(debugViewModel)
            }
        }
    }
}