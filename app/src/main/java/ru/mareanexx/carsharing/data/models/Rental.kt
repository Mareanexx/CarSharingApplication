package ru.mareanexx.carsharing.data.models

import java.math.BigDecimal
import java.time.LocalDateTime

data class Rental(
    // данные об аренде
    val idRental: Int,
    val rentalType: String,
    val pricePerSmth: BigDecimal,
    var startTime: LocalDateTime?,
    // данные об авто
    val idCar: Int,
    val brand: String,
    val model: String,
    val licencePlate: String,
    val imagePath: String,
    val fuelLevel: Int,
    val fuelTankCapacity: Int,
)

data class RentalHistory(
    val startTime: LocalDateTime,
    val totalPrice: BigDecimal,
    val brand: String,
    val model: String
)

data class NewReservationRequest(
    val rentalType: String,
    val pricePerSmth: BigDecimal,
    val idCar: Int, // Foreign Key to car
    val idUser: Int // Foreign Key to user
)

data class NewRentalRequest(
    val idRental: Int,
)

data class NewRentalResponse(
    val startTime: LocalDateTime
)

data class ClosedRentalRequest(
    val idRental: Int,
)

data class CompletedRentalRequest(
    val idRental: Int,
    // обновить поля
    val totalPrice: BigDecimal,
    val distance: Int,
    val duration: Int,
)