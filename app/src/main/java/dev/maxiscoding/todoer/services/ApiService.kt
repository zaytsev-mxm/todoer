package dev.maxiscoding.todoer.services

import dev.maxiscoding.todoer.model.RegisterRequest
import dev.maxiscoding.todoer.model.TokenResponse
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Body

private val retrofit = Retrofit.Builder()
    .baseUrl("https://maxiscoding.dev/todolist/api/v1.0/")
    // .baseUrl("http://10.0.2.2:8080/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService: ApiService = retrofit.create(ApiService::class.java)

interface ApiService {
    @POST("register")
    suspend fun registerUserViaEmail(@Body request: RegisterRequest): Response<TokenResponse>
}