package dev.maxiscoding.todoer.repository

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.maxiscoding.todoer.TOKEN_PREF
import dev.maxiscoding.todoer.debug.dataStore
import dev.maxiscoding.todoer.model.LoginRequest
import dev.maxiscoding.todoer.model.RegisterRequest
import dev.maxiscoding.todoer.model.TokenResponse
import dev.maxiscoding.todoer.services.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore

    var token by mutableStateOf<String?>(null)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.data
                .map { preferences -> preferences[TOKEN_PREF] }
                .distinctUntilChanged()
                .collect { value ->
                    token = value
                }
        }
    }

    suspend fun setToken(token: String?) {
        dataStore.edit { settings ->
            if (token == null) {
                settings.remove(TOKEN_PREF)
            } else {
                settings[TOKEN_PREF] = token
            }
        }
    }

    suspend fun registerUserViaEmail(request: RegisterRequest): Response<TokenResponse> {
        return apiService.registerUserViaEmail(request)
    }

    suspend fun loginUserViaEmail(request: LoginRequest): Response<TokenResponse> {
        return apiService.loginUserViaEmail(request)
    }
}