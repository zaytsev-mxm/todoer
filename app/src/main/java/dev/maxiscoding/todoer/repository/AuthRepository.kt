package dev.maxiscoding.todoer.repository

import dev.maxiscoding.todoer.model.LoginRequest
import dev.maxiscoding.todoer.model.RegisterRequest
import dev.maxiscoding.todoer.model.TokenResponse
import dev.maxiscoding.todoer.services.ApiService
import dev.maxiscoding.todoer.services.DataStoreService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val dataStoreService: DataStoreService,
) {
    fun observeTokenFlow() = dataStoreService.getTokenPrefFlow()

    suspend fun setToken(token: String?) {
        dataStoreService.updateTokenPref(token)
    }

    suspend fun registerUserViaEmail(request: RegisterRequest): Response<TokenResponse> {
        return apiService.registerUserViaEmail(request)
    }

    suspend fun loginUserViaEmail(request: LoginRequest): Response<TokenResponse> {
        return apiService.loginUserViaEmail(request)
    }
}