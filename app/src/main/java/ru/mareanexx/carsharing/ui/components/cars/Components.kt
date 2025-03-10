package ru.mareanexx.carsharing.ui.components.cars

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.mareanexx.carsharing.R
import ru.mareanexx.carsharing.data.models.Car
import ru.mareanexx.carsharing.data.models.NewReservationRequest
import ru.mareanexx.carsharing.ui.components.auth.AuthButton
import ru.mareanexx.carsharing.ui.theme.backgroundBtn
import ru.mareanexx.carsharing.ui.theme.backgroundCarCard
import ru.mareanexx.carsharing.ui.theme.backgroundFrame
import ru.mareanexx.carsharing.ui.theme.black
import ru.mareanexx.carsharing.ui.theme.blackBtn
import ru.mareanexx.carsharing.ui.theme.btnMinutesColor
import ru.mareanexx.carsharing.ui.theme.carNameColor
import ru.mareanexx.carsharing.ui.theme.characteristicCar
import ru.mareanexx.carsharing.ui.theme.cherry
import ru.mareanexx.carsharing.ui.theme.prevBtn
import ru.mareanexx.carsharing.ui.theme.textInFrame
import ru.mareanexx.carsharing.ui.theme.titleTextColor
import ru.mareanexx.carsharing.ui.theme.valueTextColor
import ru.mareanexx.carsharing.ui.theme.white
import ru.mareanexx.carsharing.ui.viewmodel.ReservationViewModel
import java.math.BigDecimal
import kotlin.random.Random


// Карточка авто на экране CarAtLocation
@Composable
fun CarCard(
    car: Car,
    context: Context,
    onClick: () -> Unit
) {
    val resourceId = remember(car.imagePath) {
        context.resources.getIdentifier(car.imagePath, "drawable", context.packageName)
    }
    val carImage = if (resourceId != 0) {
        painterResource(id = resourceId)
    } else {
        painterResource(id = R.drawable.havaljolion)
    }

    Column(
        modifier = Modifier.padding(top = 10.dp).background(
            color = backgroundCarCard,
            shape = RoundedCornerShape(10.dp)
        ).padding(20.dp).clickable(onClick = onClick)
    ) {
        Row {
            Column {
                // Бренд + модель машины
                Text(
                    text = "${car.brand} ${car.model}",
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = carNameColor,
                    modifier = Modifier.widthIn(max = 115.dp)
                )
                Text(
                    text = car.licencePlate,
                    fontSize = 13.sp,
                    color = textInFrame,
                    modifier = Modifier.padding(top = 5.dp).background(
                        color = backgroundFrame,
                        shape = RoundedCornerShape(4.dp)
                    ).padding(vertical = 2.dp, horizontal = 8.dp)
                )
                Text(
                    text = makeDistanceAndFuelText(car.fuelLevel, car.fuelTankCapacity),
                    fontSize = 13.sp,
                    color = textInFrame,
                    modifier = Modifier.padding(top = 8.dp).background(
                        color = backgroundFrame,
                        shape = RoundedCornerShape(4.dp)
                    ).padding(vertical = 2.dp, horizontal = 8.dp)
                )
            }
            Image(
                painter = carImage,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )

        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            ButtonRentByTime("Минуты", "мин", "${car.pricePerMinute}", Modifier.weight(0.5f), btnMinutesColor, 16.sp, 11.sp) { }
            Spacer(modifier = Modifier.width(10.dp))
            ButtonRentByTime("Часы", "час", "${car.pricePerHour}", Modifier.weight(0.5f), carNameColor, 16.sp, 11.sp) { }
        }
    }
}

// Кнопки внизу карточки
@Composable
fun ButtonRentByTime(
    minuteOrHour: String,
    textSmall: String,
    price: String,
    modifier: Modifier,
    containerColor: Color,
    nameTextSize: TextUnit,
    valueTextSize: TextUnit,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(7.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        contentPadding = PaddingValues(vertical = 4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = minuteOrHour,
                fontSize = nameTextSize,
                lineHeight = 8.sp
            )
            Text(
                text = "от $price ₽/$textSmall",
                fontSize = valueTextSize,
                lineHeight = 8.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

fun makeDistanceAndFuelText(
    fuelLevel: Int,
    fuelTankCapacity: Int
) : String {
    return "${Random.nextInt(100, 400)} км - ${(fuelLevel * 100) / fuelTankCapacity}%"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDetailsBottomSheetContent(
    car: Car,
    idUser: Int,
    context: Context,
    navController: NavController,
    onClose: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    var chosenType by remember { mutableStateOf("") }
    var chosenTypeShort by remember { mutableStateOf("") }
    var chosenPrice by remember { mutableStateOf(BigDecimal(0)) }

    val resourceId = remember(car.imagePath) {
        context.resources.getIdentifier(car.imagePath, "drawable", context.packageName)
    }
    val carImage = if (resourceId != 0) {
        painterResource(id = resourceId)
    } else {
        painterResource(id = R.drawable.havaljolion)
    }

    Column(
        modifier = Modifier.background(
            color = white,
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
        ).padding(horizontal = 30.dp, vertical = 20.dp).fillMaxWidth() // здесь был vertical = 20.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${car.brand} ${car.model}",
                color = black,
                fontWeight = FontWeight.Medium,
                lineHeight = 20.sp,
                fontSize = 26.sp
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.background(
                    color = prevBtn,
                    shape = RoundedCornerShape(10.dp)
                ).padding(4.dp).clickable(onClick = onClose),
                tint = titleTextColor
            )
        }
        Row(
            modifier = Modifier.padding(top = 8.dp, bottom = 14.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = car.licencePlate,
                fontSize = 13.sp,
                color = textInFrame,
                modifier = Modifier.padding(end = 10.dp).background(
                    color = backgroundFrame,
                    shape = RoundedCornerShape(4.dp)
                ).padding(vertical = 2.dp, horizontal = 8.dp)
            )
            Text(
                text = makeDistanceAndFuelText(car.fuelLevel, car.fuelTankCapacity),
                fontSize = 13.sp,
                color = textInFrame,
                modifier = Modifier.background(
                    color = backgroundFrame,
                    shape = RoundedCornerShape(4.dp)
                ).padding(vertical = 2.dp, horizontal = 8.dp)
            )
        }
        Image(
            painter = carImage,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier.padding(top = 13.dp, bottom = 15.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Характеристики",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = titleTextColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 2.dp).fillMaxWidth()
            )
            CharacteristicCar("КПП тип", makeTransmissionString(car.transmission))
            CharacteristicCar("Привод", makeDriveTypeString(car.driveType))
            CharacteristicCar("Объем двигателя", "${car.engineVolume} л")
            CharacteristicCar("Мощность", "${car.enginePower} л.с.")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BooleanCharacteristicCar("Сенсорный экран", car.touchScreen, "Подогрев сидений", car.heatedSeats)
            BooleanCharacteristicCar("Подогрев руля", car.heatedSteeringWheel, "Парктроники", car.parkingSensors)
        }
        Column(
            modifier = Modifier.padding(top = 25.dp).fillMaxWidth()
        ) {
            ButtonRentByTime("Минуты", "мин", "${car.pricePerMinute}", Modifier.fillMaxWidth(), btnMinutesColor, 16.sp, 11.sp) {
                chosenType = "Минуты"
                chosenPrice = car.pricePerMinute
                chosenTypeShort = "мин"
                showBottomSheet = true
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                ButtonRentByTime("Часы", "час", "${car.pricePerHour}", Modifier.weight(0.5f), carNameColor, 16.sp, 11.sp) {
                    chosenType = "Часы"
                    chosenPrice = car.pricePerHour
                    chosenTypeShort = "час"
                    showBottomSheet = true
                }
                Spacer(modifier = Modifier.width(10.dp))
                ButtonRentByTime("Дни", "день", "${car.pricePerDay}", Modifier.weight(0.5f), cherry, 16.sp, 11.sp) {
                    chosenType = "Дни"
                    chosenPrice = car.pricePerDay
                    chosenTypeShort = "день"
                    showBottomSheet = true
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                scrimColor = titleTextColor,
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                containerColor = white,
                dragHandle = null,
                sheetState = sheetState
            ) {
                RentalDetailsBottomSheet(
                    idUser = idUser,
                    idCar = car.idCar,
                    chosenType = chosenType,
                    chosenTypeShort = chosenTypeShort,
                    chosenPrice = chosenPrice,
                    navigateTo = { navController.navigate("home_map/$idUser") }
                ) {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BooleanCharacteristicCar(
    firstText: String,
    first: Boolean,
    secondText: String,
    second: Boolean
) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 5.dp)
        ) {
            Text(
                text = firstText,
                fontSize = 16.sp,
                color = titleTextColor,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(end = 10.dp)
            )
            MakeTrueOrFalseIcon(first)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = secondText,
                fontSize = 16.sp,
                color = titleTextColor,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(end = 10.dp)
            )
            MakeTrueOrFalseIcon(second)
        }
    }
}

@Composable
fun MakeTrueOrFalseIcon(
    boolean: Boolean
) {
    val icon = if (boolean) Icons.Default.Check else Icons.Default.Close

    Box(
        modifier = Modifier
            .size(23.dp)
            .clip(RoundedCornerShape(5.dp))
            .border(
                width = 1.3.dp,
                color = titleTextColor,
                shape = RoundedCornerShape(5.dp)
            )
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Close",
            tint = titleTextColor,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun CharacteristicCar(
    textType: String,
    textValue: String
) {
    Row(
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Text(
            text = textType,
            fontSize = 16.sp,
            color = titleTextColor,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(end = 10.dp)
        )
        Text(
            text = textValue,
            fontSize = 16.sp,
            color = characteristicCar,
            fontWeight = FontWeight.Medium
        )
    }
}

fun makeTransmissionString(type: String) = if (type == "AUTOMATIC") "Автомат" else "Механика"

fun makeDriveTypeString(type: String) = when(type) {
    "FWD" -> "Передний"
    "AWD" -> "Полный"
    "RWD" -> "Задний"
    else -> "Никакой"
}

@Composable
fun RentalDetailsBottomSheet(
    idUser: Int,
    idCar: Int,
    reservationViewModel: ReservationViewModel = viewModel(),
    chosenType: String,
    chosenTypeShort: String,
    chosenPrice: BigDecimal,
    navigateTo: () -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier.background(
            color = white,
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
        ).padding(horizontal = 30.dp, vertical = 20.dp).fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(bottom = 7.dp)
        ) {
            Text(
                text = chosenType,
                color = black,
                fontWeight = FontWeight.Medium,
                lineHeight = 20.sp,
                fontSize = 26.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.background(
                    color = prevBtn,
                    shape = RoundedCornerShape(10.dp)
                ).padding(4.dp).clickable(onClick = onClose),
                tint = titleTextColor
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            RentalBlockText(
                painterResource(id = R.drawable.schedule),
                textTitle = "5 минут",
                bottomText = "Время бесплатной брони"
            )
            RentalBlockText(
                painterResource(id = R.drawable.directions_car),
                textTitle = "$chosenPrice руб/$chosenTypeShort",
                bottomText = "Выбранный тариф"
            )

        }
        Spacer(modifier = Modifier.height(20.dp))
        AuthButton(white, blackBtn, "ЗАБРОНИРОВАТЬ") {
            val rentalType = when(chosenType) {
                "Минуты" -> "MINUTE"
                "Часы" -> "HOUR"
                "Дни" -> "DAY"
                else -> "Nothing"
            }
            reservationViewModel.makeReservation(
                NewReservationRequest(
                    pricePerSmth = chosenPrice,
                    idCar = idCar, idUser = idUser,
                    rentalType = rentalType
                ),
                onSuccess = { navigateTo() },
            )
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun RentalBlockText(
    icon : Painter,
    textTitle: String,
    bottomText: String
) {
    Row(
        modifier = Modifier.padding(top = 10.dp).fillMaxWidth().background(
            color = backgroundBtn,
            shape = RoundedCornerShape(10.dp)
        ).padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = "Time",
            tint = titleTextColor,
            modifier = Modifier.size(27.dp)
        )
        Column(
            modifier = Modifier.padding(start = 15.dp)
        ) {
            Text(
                text = textTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = titleTextColor,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = bottomText,
                fontSize = 12.sp,
                color = valueTextColor,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


//@Preview(showSystemUi = true)
//@Composable
//fun PreviewCarDetails() {
//    Column(
//        modifier = Modifier.padding(top = 50.dp)
//    ) {
//        CarDetailsBottomSheetContent(
//            Car(1, "Audi", "Q5", "А 476 МН 797", "audiq5",
//                90, 70, "AUTOMATIC", "AWD", 2.0.toBigDecimal(), 190,
//                true, false, true, true, 10.4.toBigDecimal(), 340.toBigDecimal(), 4543.4.toBigDecimal())
//        , context = LocalContext.current) { }
////        RentalDetailsBottomSheet("Минуты", "мин", "15.45") {}
//    }
//}