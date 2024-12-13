package ru.mareanexx.carsharing.data.models

import java.time.LocalDateTime

data class UserRequestRequest(
    val messageTitle: String,
    val message: String,
    val idUser: Int
)

data class UserRequestResponse(
    val idRequest: Int,
    val messageTitle: String,
    val message: String,
    val createdAt: LocalDateTime,
    val status: String,
    val idUser: Int // Foreign Key to user
)