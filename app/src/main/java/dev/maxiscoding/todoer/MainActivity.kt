package dev.maxiscoding.todoer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.maxiscoding.todoer.repository.AuthRepository
import dev.maxiscoding.todoer.screens.debug.Debug
import dev.maxiscoding.todoer.screens.homeauth.HomeAuthorised
import dev.maxiscoding.todoer.screens.homeguest.HomeGuest
import dev.maxiscoding.todoer.ui.theme.ToDoErTheme
import javax.inject.Inject

sealed class Screen(val route: String) {
    data object HomeGuest : Screen("HomeGuest")
    data object HomeAuthorised : Screen("HomeAuthorised")
    data object Debug : Screen("Debug")
}

val DefaultRoute = Screen.Debug.route

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoErTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        val viewModel: AppViewModel = hiltViewModel()

                        val navController = rememberNavController()
                        CompositionLocalProvider(
                            LocalAppViewModel provides viewModel
                        ) {
                            NavHost(navController = navController, startDestination = DefaultRoute) {
                                composable(Screen.HomeGuest.route) {
                                    HomeGuest {
                                        navController.navigate(Screen.HomeAuthorised.route)
                                    }
                                }
                                composable(Screen.HomeAuthorised.route) {
                                    HomeAuthorised()
                                }
                                composable(Screen.Debug.route) {
                                    Debug()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}