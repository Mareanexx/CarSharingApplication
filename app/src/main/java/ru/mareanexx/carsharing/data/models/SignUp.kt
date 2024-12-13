package ru.mareanexx.carsharing.data.models

data class UserRegistrationRequest(
    val username: String,
    val password: String,
    val email: String,
    val phoneNumber: String
)