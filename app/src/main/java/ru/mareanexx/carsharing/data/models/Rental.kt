package ru.mareanexx.carsharing.data.models

import java.math.BigDecimal
import java.time.LocalDateTime

data class RentalHistory(
    val startTime: LocalDateTime,
    val totalPrice: BigDecimal,
    val brand: String,
    val model: String
)