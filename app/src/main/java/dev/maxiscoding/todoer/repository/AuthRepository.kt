package dev.maxiscoding.todoer.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.maxiscoding.todoer.TOKEN_PREF
import dev.maxiscoding.todoer.debug.dataStore
import dev.maxiscoding.todoer.model.LoginRequest
import dev.maxiscoding.todoer.model.RegisterRequest
import dev.maxiscoding.todoer.model.TokenResponse
import dev.maxiscoding.todoer.services.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore

    val tokenFlow: Flow<String?> = dataStore.data.map { preferences -> preferences[TOKEN_PREF] }

    suspend fun setToken(token: String?) {
        dataStore.edit { settings ->
            if (token == null) {
                settings.remove(TOKEN_PREF)
                return@edit
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