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
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import androidx.activity.viewModels

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

    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { !isAppReady }

        lifecycleScope.launch {
            viewModel.uiState.map {
                it.hasInitialDataReceived
            }.collect { value ->
                isAppReady = value
            }
        }

        enableEdgeToEdge()
        setContent {
            ToDoErTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        val viewModel: AppViewModel = hiltViewModel()
                        val navController = rememberNavController()
                        val currentBackStackEntry by navController.currentBackStackEntryAsState()

                        CompositionLocalProvider(
                            LocalAppViewModel provides viewModel
                        ) {
                            NavHost(
                                navController = navController,
                                startDestination = DefaultRoute
                            ) {
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

                            LaunchedEffect(Unit) {
                                viewModel.uiState
                                    .map { it.token }
                                    .distinctUntilChanged()
                                    .collect { token ->
                                        if (token == null &&
                                            currentBackStackEntry?.destination?.route != Screen.HomeGuest.route
                                        ) {
                                            navController.navigate(Screen.HomeGuest.route) {
                                                popUpTo(0)
                                            }
                                        }
                                    }
                            }
                        }
                    }
                }
            }
        }
    }
}