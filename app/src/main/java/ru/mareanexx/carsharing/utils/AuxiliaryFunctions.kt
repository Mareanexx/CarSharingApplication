package ru.mareanexx.carsharing.utils

import ru.mareanexx.carsharing.data.models.Rental
import java.math.BigDecimal
import java.time.Duration


// Вспомогательная функция для расчета стоимости
fun calculateTotalCost(rental: Rental, duration: Duration): BigDecimal {
    val price = rental.pricePerSmth
    return when (rental.rentalType) {
        "MINUTE" -> price * duration.toMinutes().toBigDecimal()
        "HOUR" -> price * duration.toHours().toBigDecimal()
        "DAY" -> price * duration.toDays().toBigDecimal()
        else -> BigDecimal.ZERO
    }
}

// Определение интервала обновления
fun getUpdateIntervalMillis(rentalType: String): Long {
    return when (rentalType) {
        "MINUTE" -> 60_000L // 1 минута
        "HOUR" -> 3_600_000L // 1 час
        "DAY" -> 86_400_000L // 1 день
        else -> 1_000L // Безопасный fallback
    }
}