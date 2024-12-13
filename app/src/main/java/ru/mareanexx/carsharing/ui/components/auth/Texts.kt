package ru.mareanexx.carsharing.ui.components.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.carsharing.ui.theme.errorLogin


// Текст сверху Log In, Sign Up на экранах регистрации и авторизации
@Composable
fun PartTextLogin(textColor: Color, text: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        color = textColor,
        text = text,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 44.sp,
        letterSpacing = 0.5.sp
    )
}

// Текст для правил обработки персональных данных
@Composable
fun PartTextRules(text: String, color: Color) {
    Text(
        modifier = Modifier,
        text = text,
        color = color,
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.3.sp
    )
}

// Текст ошибки для экрана регистрации и авторизации
@Composable
fun ErrorLoginText() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.height(50.dp).fillMaxWidth()
    ) {
        Text(
            text = "Неверно указаны почта или пароль",
            color = errorLogin
        )
    }
}


@Composable
fun ErrorRegText() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.height(30.dp).fillMaxWidth()
    ) {
        Text(
            text = "Пользователь с такой почтой уже существует",
            color = errorLogin
        )
    }
}


