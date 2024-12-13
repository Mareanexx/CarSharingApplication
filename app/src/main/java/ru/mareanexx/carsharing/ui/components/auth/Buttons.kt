package ru.mareanexx.carsharing.ui.components.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.carsharing.R
import ru.mareanexx.carsharing.ui.theme.prevBtn

// Кнопка "Войти" или "Зарегистрироваться"
@Composable
fun AuthButton(
    textColor: Color,
    btnColor: Color,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = btnColor,
            contentColor = textColor
        ),
        shape = RoundedCornerShape(10.dp),
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            letterSpacing = 0.6.sp
        )
    }
}

// Кнопка с картинкой < - вернуться назад
@Composable
fun PreviousButtonLogin(onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.padding(start = 20.dp).height(34.dp).width(34.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Image(
            modifier = Modifier.width(11.dp),
            painter = painterResource(id = R.drawable.arrow_back_1),
            contentDescription = "Стрелка назад"
        )
    }
}


@Preview
@Composable
fun PreviousButtonLoginPreview() {
    PreviousButtonLogin {
        println("nothing")
    }
}

