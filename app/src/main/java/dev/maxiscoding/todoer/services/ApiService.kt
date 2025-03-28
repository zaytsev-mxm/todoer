package dev.maxiscoding.todoer.services

import dev.maxiscoding.todoer.model.LoginRequest
import dev.maxiscoding.todoer.model.RegisterRequest
import dev.maxiscoding.todoer.model.TokenResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiService {
    @POST("register")
    suspend fun registerUserViaEmail(@Body request: RegisterRequest): Response<TokenResponse>

    @POST("login")
    suspend fun loginUserViaEmail(@Body request: LoginRequest): Response<TokenResponse>
}