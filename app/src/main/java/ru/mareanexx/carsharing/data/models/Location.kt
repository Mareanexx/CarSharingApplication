package ru.mareanexx.carsharing.data.models

import java.math.BigDecimal

data class Location(
    val idLocation: Int,
    val name: String,
    val address: String,
    val latitude: BigDecimal,
    val longitude: BigDecimal
)