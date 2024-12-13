package ru.mareanexx.carsharing.ui.components.sidepanel

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.carsharing.ui.theme.backgroundBlock
import ru.mareanexx.carsharing.ui.theme.backgroundBtn
import ru.mareanexx.carsharing.ui.theme.backgroundTheme
import ru.mareanexx.carsharing.ui.theme.black
import ru.mareanexx.carsharing.ui.theme.titleTextColor
import ru.mareanexx.carsharing.ui.theme.valueTextColor


// Один блок в персональном информации
@Composable
fun PersonalInfoRowOfInfo(
    icon: ImageVector,
    titleText: String,
    valueText: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = valueTextColor,
            modifier = Modifier.height(30.dp).width(30.dp)
        )
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(start = 15.dp)
        ) {
            Text(
                text = titleText,
                color = titleTextColor,
                fontSize = 16.sp,
                letterSpacing = 0.1.sp
            )
            Text(
                text = valueText,
                color = valueTextColor,
                fontSize = 14.sp,
                letterSpacing = 0.1.sp,
                modifier = Modifier.padding(top = 3.dp)
            )
        }
    }
}

// Один блок в Истории поездок
@Composable
fun RentalHistoryElement(
    date: String,
    carName: String,
    price: String
) {
    Row(
        modifier = Modifier.background(
            color = backgroundBlock,
            shape = RoundedCornerShape(12.dp)
        ).padding(horizontal = 22.dp, vertical = 16.dp).fillMaxWidth()
    ) {
        Column {
            Text(
                text = date,
                color = titleTextColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Text(
                text = carName,
                color = valueTextColor,
                fontSize = 13.sp
            )
        }
        // Промежуток, который "толкает" текст к правому краю
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = price,
            color = titleTextColor,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
    }
}

// Блок переключатель (История или Новое) на экране SupportScreen
@Composable
fun SwitcherHistoryOrNew(
    historyButtonColor: Color,
    newButtonColor: Color,
    historyTextColor: Color,
    newTextColor: Color,
    onClickHistory: () -> Unit,
    onClickNew: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier.weight(0.5f),
            shape = RoundedCornerShape(15.dp),
            onClick = onClickHistory,
            colors = ButtonDefaults.buttonColors(containerColor = historyButtonColor)
        ) {
            Text(
                text = "История",
                color = historyTextColor,
                fontWeight = FontWeight.Medium
            )
        }

        Button(
            modifier = Modifier.weight(0.5f),
            shape = RoundedCornerShape(15.dp),
            onClick = onClickNew,
            colors = ButtonDefaults.buttonColors(containerColor = newButtonColor)
        ) {
            Text(
                text = "Новое",
                color = newTextColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// Блок с ОТКРЫТЫМ ОБРАЩЕНИЕМ пользователя
@Composable
fun UserRequestItemBlock(
    themeTitle: String,
    date: String,
    status: String,
    requestText: String
)
{
    // Состояние для управления видимостью блока
    val isExpanded = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp).background(
            color = backgroundBlock,
            shape = RoundedCornerShape(15.dp)
        ).padding(7.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(
                color = backgroundTheme,
                shape = RoundedCornerShape(13.dp)
            ).padding(horizontal = 15.dp, vertical = 9.dp)
                .clickable { isExpanded.value = !isExpanded.value },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Тема",
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                modifier = Modifier.padding(end = 20.dp)
            )
            Text(
                text = themeTitle,
                color = titleTextColor
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (isExpanded.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
        }

        // Контент который будет раскрываться или закрываться
        if (isExpanded.value) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Дата",
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(end = 20.dp)
                    )
                    Text(
                        text = date, // в формате "04 декабря 2024, 14:21"
                        color = titleTextColor
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 9.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Статус",
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(end = 20.dp)
                    )
                    Text(
                        text = status,
                        color = titleTextColor
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 9.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Текст",
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(end = 20.dp)
                    )
                    Text(
                        text = requestText,
                        color = titleTextColor
                    )
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewComponent() {
//    PersonalInfoRowOfInfo(
//        Icons.Default.Email,
//        "E-mail",
//        "mareanexx@mail.ru")
    Column(
        modifier = Modifier.padding(20.dp).fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(58.dp))
        RentalHistoryElement("06 декабря 2024, 20:09",
            "Haval Jolion", "1 557,81" + " ₽")
    }
}