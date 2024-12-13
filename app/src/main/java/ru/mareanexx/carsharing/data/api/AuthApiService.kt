package ru.mareanexx.carsharing.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.mareanexx.carsharing.data.models.LoginRequest
import ru.mareanexx.carsharing.data.models.LoginResponse
import ru.mareanexx.carsharing.data.models.UserRegistrationRequest


interface AuthApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("register")
    suspend fun register(@Body request: UserRegistrationRequest): Response<Map<String, String>>
}