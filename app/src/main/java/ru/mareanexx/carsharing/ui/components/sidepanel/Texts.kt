package ru.mareanexx.carsharing.ui.components.sidepanel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.carsharing.ui.theme.black

@Composable
fun MainTextInTitleZone(
    title: String
) {
    Text(
        text = title,
        fontSize = 30.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 0.5.sp,
        color = black,
        modifier = Modifier.padding(vertical = 20.dp)
    )
}

@Composable
fun MainLocationTitle(
    title: String
) {
    Text(
        text = title,
        fontSize = 30.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 0.5.sp,
        color = black,
        modifier = Modifier.padding(0.dp)
    )
}


// Текст уведомления отправлено ли обращение пользователя.
@Composable
fun NotifNewRequestText(
    text: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.height(40.dp).fillMaxWidth().padding(bottom = 4.dp)
    ) {
        Text(
            text = text,
            color = color // errorLogin
        )
    }
}