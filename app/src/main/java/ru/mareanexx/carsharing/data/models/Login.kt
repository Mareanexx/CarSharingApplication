package ru.mareanexx.carsharing.data.models

// Логин запрос
data class LoginRequest(
    val email: String,
    val password: String
)

// Ответ от сервера на логин
data class LoginResponse(
    val idUser: Int,
    val accessToken: String,
    val tokenType: String = "Bearer"
)