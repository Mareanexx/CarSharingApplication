package ru.mareanexx.carsharing.ui.components.rental

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ru.mareanexx.carsharing.R
import ru.mareanexx.carsharing.data.models.Rental
import ru.mareanexx.carsharing.ui.components.auth.AuthButton
import ru.mareanexx.carsharing.ui.components.cars.makeDistanceAndFuelText
import ru.mareanexx.carsharing.ui.theme.backgroundBtn
import ru.mareanexx.carsharing.ui.theme.backgroundFrame
import ru.mareanexx.carsharing.ui.theme.black
import ru.mareanexx.carsharing.ui.theme.blackBtn
import ru.mareanexx.carsharing.ui.theme.lineAroundTitle
import ru.mareanexx.carsharing.ui.theme.prevBtn
import ru.mareanexx.carsharing.ui.theme.regBtn
import ru.mareanexx.carsharing.ui.theme.regBtnText
import ru.mareanexx.carsharing.ui.theme.textInFrame
import ru.mareanexx.carsharing.ui.theme.titleTextColor
import ru.mareanexx.carsharing.ui.theme.valueTextColor
import ru.mareanexx.carsharing.ui.theme.white
import ru.mareanexx.carsharing.utils.calculateTotalCost
import ru.mareanexx.carsharing.utils.getUpdateIntervalMillis
import java.math.BigDecimal
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale

fun makeFullNameAndShortName(
    rentalType: String
) : Pair<String, String> {
    return when(rentalType) {
        "MINUTE" -> Pair("Минуты", "мин")
        "HOUR" -> Pair("Часы", "час")
        "DAY" -> Pair("Дни", "день")
        else -> Pair("Ничего", "ничего")
    }
}

@Composable
fun RentalBottomSheetOnMapScreen(
    rental: Rental?,
    context: Context,
    isRentingParam: Boolean,
    onCompleteRent: (BigDecimal, Int) -> Unit,
    onStartRent: () -> Unit,
    onRentCancel: () -> Unit,
    onClose: () -> Unit
) {
    // Для закрытия модального окна, когда нажимается ОТМЕНИТЬ
    if (rental == null) {
        return
    }

    // Для картинки ресурс ищется по имени
    val resourceId = remember(rental.imagePath) {
        context.resources.getIdentifier(rental.imagePath, "drawable", context.packageName)
    }
    val carImage = if (resourceId != 0) {
        painterResource(id = resourceId)
    } else {
        painterResource(id = R.drawable.havaljolion)
    }

    var isRenting by remember { mutableStateOf(isRentingParam) }

    Column(
        modifier = Modifier
            .background(
                color = white,
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            )
    ) {
        // АРЕНДЫ и кнопка закрыть
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                .fillMaxWidth() // здесь был vertical = 20.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Аренда",
                    color = black,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 20.sp,
                    fontSize = 26.sp
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .background(
                            color = prevBtn,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(4.dp)
                        .clickable(onClick = onClose),
                    tint = titleTextColor
                )
            }
        }

        // Машина инфа
        Row(
            modifier = Modifier.padding(start = 20.dp, top = 12.dp).fillMaxWidth().padding( top = 10.dp, bottom = 15.dp)
        ) {
            Column(
                modifier = Modifier.padding(start = 15.dp, end = 5.dp)
            ) {
                Text(
                    text = "${rental.brand} ${rental.model}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = titleTextColor,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = rental.licencePlate,
                    fontSize = 13.sp,
                    color = textInFrame,
                    modifier = Modifier.padding(bottom = 5.dp).background(
                        color = backgroundFrame,
                        shape = RoundedCornerShape(4.dp)
                    ).padding(vertical = 2.dp, horizontal = 8.dp)
                )
                Text(
                    text = makeDistanceAndFuelText(rental.fuelLevel, rental.fuelTankCapacity),
                    fontSize = 13.sp,
                    color = textInFrame,
                    modifier = Modifier.background(
                        color = backgroundFrame,
                        shape = RoundedCornerShape(4.dp)
                    ).padding(vertical = 2.dp, horizontal = 8.dp)
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = carImage,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    alignment = Alignment.CenterStart,
                    contentScale = ContentScale.Crop
                )
            }
        }

        // ТАРИФ АРЕНДЫ
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 15.dp)
                .fillMaxWidth()
                .background(
                    color = backgroundBtn,
                    shape = RoundedCornerShape(10.dp)
                ).padding(start = 15.dp, top = 10.dp, end = 15.dp, bottom = 15.dp) // здесь был vertical = 20.dp
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Тариф аренды",
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = titleTextColor
                )
                Text(
                    text = "${rental.pricePerSmth}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = titleTextColor
                )
            }
            val (fullName, shortName) = makeFullNameAndShortName(rental.rentalType)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = fullName,
                    fontSize = 12.sp,
                    color = valueTextColor
                )
                Text(
                    text = "руб./$shortName",
                    fontSize = 12.sp,
                    color = valueTextColor
                )
            }
        }

        // Внизу такая панелька
        Box {
            Image(
                painter = painterResource(id = R.drawable.background_map),
                contentDescription = null,
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(270.dp)
            )
            androidx.compose.animation.AnimatedVisibility(
                visible = !isRenting
            ) {
                RentalBottomBlockReservation(
                    onChangeBlock = {
                        isRenting = true
                    },
                    onStartRent = onStartRent,
                    onRentCancel = onRentCancel
                )
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = isRenting
            ) {
                RentalBottomBlockRent(
                    onCompleteRent = onCompleteRent,
                    rental = rental
                )
            }
        }
    }
}

@Composable
fun RentalBottomBlockReservation(
    onChangeBlock: () -> Unit,
    onStartRent: () -> Unit,
    onRentCancel: () -> Unit,
) {
    var timeLeft by remember { mutableIntStateOf(300) } // 5 минут

    LaunchedEffect(timeLeft) {
        if (timeLeft > 0) {
            delay(1000L)
            timeLeft -= 1
        } else {
            onRentCancel()
        }
    }
    val formattedTime = String.format(Locale("ru"), "%02d:%02d", timeLeft / 60, timeLeft % 60)


    // На БРОНИРОВАНИЕ 5 минутное
    Column( // horizontal = 20.dp, vertical = 22.dp
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 22.dp)
            .shadow(
                elevation = 15.dp,
                shape = RoundedCornerShape(10.dp)
            ).background(
                color = white,
                shape = RoundedCornerShape(10.dp)
            ).padding(5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp, bottom = 10.dp, top = 5.dp)
        ) {
            Column(
                modifier = Modifier.padding()
            ) {
                Text(
                    text = "Бесплатное бронирование",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = titleTextColor,
                )
                Text(
                    text = "Время бесплатной брони",
                    fontSize = 12.sp,
                    color = valueTextColor
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = formattedTime, // Должно выглядеть вот так 05:00 - 5 минут и секунды
                fontSize = 36.sp,
                fontWeight = FontWeight.Medium,
                color = titleTextColor
            )
        }
        Column(
            modifier = Modifier.padding(2.dp).fillMaxWidth().background(
                color = backgroundBtn,
                shape = RoundedCornerShape(10.dp)
            ).padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ButtonHeatAndLights("ПРОГРЕТЬ", painterResource(id = R.drawable.heat), Modifier.weight(0.5f))
                Spacer(modifier = Modifier.width(10.dp))
                ButtonHeatAndLights("ПОМОРГАТЬ", painterResource(id = R.drawable.carlight), Modifier.weight(0.5f))
            }

            AuthButton(white, blackBtn, "НАЧАТЬ АРЕНДУ") {
                onStartRent()
                onChangeBlock()
            }
            AuthButton(regBtnText, regBtn, "ОТМЕНИТЬ") {
                onRentCancel()
            } // Отмена бронирования
        }
    }

}

// ИТАК START TIME МЕНЯЕТСЯ СТО ПРОЦ!!! значит Rental не статический объект
@Composable
fun RentalBottomBlockRent(
    rental: Rental?,
    onCompleteRent: (totalPrice: BigDecimal, duration: Int) -> Unit
) {
    if (rental == null) return
    if (rental.startTime == null) {
        CircularProgressIndicator()
        return
    }

    // Запоминаем начальное время аренды
    val startTime = rental.startTime!!

    // Стейт для времени
    val elapsedTime by produceState(initialValue = Duration.ZERO, startTime) {
        while (true) {
            val now = LocalDateTime.now(ZoneId.of("Europe/Moscow"))
            value = Duration.between(startTime, now)
            delay(1000L) // Обновление каждую секунду
        }
    }

    // Форматируем время в строку "Дни:Часы:Минуты"
    val formattedTime = String.format(
        Locale("ru"),
        "%02d:%02d:%02d",
        elapsedTime.toDays(),
        elapsedTime.toHoursPart(),
        elapsedTime.toMinutesPart()
    )

    // Стейт для суммы
    val totalCost by produceState(initialValue = BigDecimal.ZERO, startTime) {
        while (true) {
            val now = LocalDateTime.now(ZoneId.of("Europe/Moscow"))
            val duration = Duration.between(startTime, now)
            value = calculateTotalCost(rental, duration)
            delay(getUpdateIntervalMillis(rental.rentalType))
        }
    }

    Column(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 22.dp)
            .shadow(
                elevation = 15.dp,
                shape = RoundedCornerShape(10.dp)
            ).background(
                color = white,
                shape = RoundedCornerShape(10.dp)
            ).padding(5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp, bottom = 10.dp, top = 5.dp)
        ) {
            Column(
                modifier = Modifier.padding()
            ) {
                Text(
                    text = "Бронирование",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = titleTextColor,
                )
                Text(
                    text = "Продолжительность аренды",
                    fontSize = 12.sp,
                    color = valueTextColor
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = formattedTime,
                fontSize = 36.sp,
                fontWeight = FontWeight.Medium,
                color = titleTextColor
            )
        }

        Column(
            modifier = Modifier.padding(2.dp).fillMaxWidth().background(
                color = backgroundBtn,
                shape = RoundedCornerShape(10.dp)
            ).padding(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(bottom = 8.dp).fillMaxWidth()
                    .shadow(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(10.dp)
                    ).background(
                        color = white,
                        shape = RoundedCornerShape(10.dp)
                    ).padding(horizontal = 15.dp, vertical = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Общая стоимость",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = valueTextColor
                )
                Text(
                    text = "$totalCost руб",
                    color = titleTextColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Row(
                modifier = Modifier.padding(bottom = 15.dp).fillMaxWidth()
                    .shadow(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .background(
                        color = white,
                        shape = RoundedCornerShape(10.dp)
                    ).padding(horizontal = 15.dp, vertical = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Дистанция",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = valueTextColor
                )
                Text(
                    text = "120 км",
                    color = titleTextColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            AuthButton(white, blackBtn, "ЗАВЕРШИТЬ АРЕНДУ") {
                onCompleteRent(totalCost, elapsedTime.toSecondsPart())
            }
        }
    }
}


@Composable
fun ButtonHeatAndLights(
    text: String,
    icon: Painter,
    modifier: Modifier,
) {
    Row(
        modifier = modifier.shadow(
            elevation = 5.dp,
            ambientColor = lineAroundTitle,
            shape = RoundedCornerShape(10.dp)
        ).background(
            color = white,
            shape = RoundedCornerShape(10.dp)
        ).padding(vertical = 8.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = titleTextColor,
            modifier = Modifier.padding(end = 10.dp).size(18.dp)
        )
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = titleTextColor
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewRentalBottomSheet() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        RentalBottomSheetOnMapScreen(
            Rental(1, "HOUR", 10.5.toBigDecimal(),
                LocalDateTime.now(), 1, "Haval",
                "Jolion", "A 324 BK 797",
                "havaljolion", 70, 90)
            , LocalContext.current, isRentingParam = true, onRentCancel = {}, onStartRent = {}, onCompleteRent = {
                _, _ -> println()
            }) { }
    }
}
