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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.maxiscoding.todoer.screens.debug.Debug
import dev.maxiscoding.todoer.screens.homeauth.HomeAuth
import dev.maxiscoding.todoer.screens.homeguest.HomeGuest
import dev.maxiscoding.todoer.ui.theme.ToDoErTheme
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    data object Startup : Screen("Startup")
    data object HomeGuest : Screen("HomeGuest")
    data object HomeAuthorised : Screen("HomeAuthorised")
    data object Debug : Screen("Debug")
}

val DefaultRoute = Screen.Startup.route

const val TAG = "ToDoEr"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var isAppReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { !isAppReady }

        lifecycleScope.launch {
            isAppReady = checkLoginStatus()
        }

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
                                composable(Screen.Startup.route) {
                                    val uiState by viewModel.uiState.collectAsState()

                                    LaunchedEffect(uiState.token) {
                                        if (uiState.token != null) {
                                            navController.navigate(Screen.HomeAuthorised.route) {
                                                popUpTo(Screen.Startup.route) { inclusive = true }
                                            }
                                        } else {
                                            navController.navigate(Screen.HomeGuest.route) {
                                                popUpTo(Screen.Startup.route) { inclusive = true }
                                            }
                                        }
                                    }
                                }
                                composable(Screen.HomeGuest.route) {
                                    HomeGuest(navController = navController)
                                }
                                composable(Screen.HomeAuthorised.route) {
                                    HomeAuth()
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

    private suspend fun checkLoginStatus(): Boolean {
        // the next line emulates a delay for the login status checks
        // TODO(later, add a real check for the login status)
        delay(1000)
        return true
    }
}