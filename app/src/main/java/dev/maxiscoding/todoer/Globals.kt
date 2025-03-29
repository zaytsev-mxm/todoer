package dev.maxiscoding.todoer

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.datastore.preferences.core.stringPreferencesKey

val LocalAppViewModel = staticCompositionLocalOf<AppViewModel> {
    error("No AppViewModel found!")
}

val TOKEN_PREF = stringPreferencesKey("token")
