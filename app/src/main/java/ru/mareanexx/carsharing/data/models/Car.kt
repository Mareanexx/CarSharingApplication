package ru.mareanexx.carsharing.data.models

import java.math.BigDecimal

data class Car(
    val idCar: Int,
    val brand: String,
    val model: String,
    val licencePlate: String,
    val imagePath: String,
    val fuelLevel: Int,
    val fuelTankCapacity: Int,
    val transmission: String, // transmission ENUM
    val driveType : String, // driveType ENUM
    val engineVolume: BigDecimal,
    val enginePower: Int,
    val heatedSeats: Boolean,
    val heatedSteeringWheel: Boolean,
    val parkingSensors: Boolean,
    val touchScreen: Boolean,

    val pricePerHour: BigDecimal,
    val pricePerMinute: BigDecimal,
    val pricePerDay: BigDecimal
)