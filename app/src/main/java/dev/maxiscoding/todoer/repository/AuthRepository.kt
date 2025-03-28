package dev.maxiscoding.todoer.repository

import dev.maxiscoding.todoer.model.LoginRequest
import dev.maxiscoding.todoer.model.RegisterRequest
import dev.maxiscoding.todoer.model.TokenResponse
import dev.maxiscoding.todoer.services.ApiService
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun registerUserViaEmail(request: RegisterRequest): Response<TokenResponse> {
        return apiService.registerUserViaEmail(request)
    }

    suspend fun loginUserViaEmail(request: LoginRequest): Response<TokenResponse> {
        return apiService.loginUserViaEmail(request)
    }
}