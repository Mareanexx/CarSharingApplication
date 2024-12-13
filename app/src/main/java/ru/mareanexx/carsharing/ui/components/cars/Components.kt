package ru.mareanexx.carsharing.ui.components.cars

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.carsharing.ui.theme.backgroundCarCard
import ru.mareanexx.carsharing.ui.theme.backgroundFrame
import ru.mareanexx.carsharing.ui.theme.btnMinutesColor
import ru.mareanexx.carsharing.ui.theme.carNameColor
import ru.mareanexx.carsharing.ui.theme.textInFrame


// Карточка авто на экране CarAtLocation
@Composable
fun CarCard(
    carName: String,
    plateNumber: String,
    distanceFuel: String,
    pricePerMinute: String,
    pricePerHour: String,
    carImage: Painter
) {
    Column(
        modifier = Modifier.padding(top = 10.dp).background(
            color = backgroundCarCard,
            shape = RoundedCornerShape(10.dp)
        ).padding(20.dp)
    ) {
        Row {
            Column {
                // Бренд + модель машины
                Text(
                    text = carName,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    color = carNameColor
                )
                Text(
                    text = plateNumber,
                    fontSize = 13.sp,
                    color = textInFrame,
                    modifier = Modifier.padding(top = 5.dp).background(
                        color = backgroundFrame,
                        shape = RoundedCornerShape(4.dp)
                    ).padding(vertical = 2.dp, horizontal = 8.dp)
                )
                Text(
                    text = distanceFuel,
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
            ButtonRentByTime("Минуты", pricePerMinute, Modifier.weight(0.5f), btnMinutesColor) { }
            Spacer(modifier = Modifier.width(10.dp))
            ButtonRentByTime("Часы", pricePerHour, Modifier.weight(0.5f), carNameColor) { }
        }
    }
}

// Кнопки внизу карточки
@Composable
fun ButtonRentByTime(
    minuteOrHour: String,
    price: String,
    modifier: Modifier,
    containerColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(7.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        contentPadding = PaddingValues(vertical = 2.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = minuteOrHour,
                fontSize = 13.sp,
                lineHeight = 8.sp
            )
            Text(
                text = "от $price ₽/мин",
                fontSize = 10.sp,
                lineHeight = 8.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}
