package dev.maxiscoding.todoer

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppViewModel = staticCompositionLocalOf<AppViewModel> {
    error("No AppViewModel found!")
}