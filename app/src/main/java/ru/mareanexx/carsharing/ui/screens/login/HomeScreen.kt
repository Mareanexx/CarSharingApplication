package ru.mareanexx.carsharing.ui.screens.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.mareanexx.carsharing.R
import ru.mareanexx.carsharing.ui.components.auth.AuthButton
import ru.mareanexx.carsharing.ui.theme.blackBtn
import ru.mareanexx.carsharing.ui.theme.blackText
import ru.mareanexx.carsharing.ui.theme.cherry
import ru.mareanexx.carsharing.ui.theme.lineAboutService
import ru.mareanexx.carsharing.ui.theme.regBtn
import ru.mareanexx.carsharing.ui.theme.regBtnText
import ru.mareanexx.carsharing.ui.theme.white

@Composable
fun HomeScreen(navController: NavController? = null) {
    Column(
        modifier = Modifier.fillMaxSize().background(white)
    ) {
        Image(
            painter = painterResource(id = R.drawable.cars_login),
            contentDescription = "Autos",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().padding(top = 50.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 30.dp, top = 30.dp)
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.Start
            ) {
                TextMain(text = "Shérimobile — ", color = cherry)
                TextMain(text = "сервис поминутной", color = blackText)
                TextMain(text = "аренды авто", color = blackText)

                Column(
                    modifier = Modifier.width(IntrinsicSize.Max).padding(top = 18.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 2.dp),
                        text = "Подробнее о сервисе",
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp,
                        letterSpacing = 0.6.sp
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = lineAboutService
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, bottom = 40.dp)
        ) {
            AuthButton(white, blackBtn, "ВОЙТИ") {
                navController?.navigate("login") ?: Log.e("NAV_CONTROLLER", "Cant navigate to login")
            } // здесь onClick }
            Spacer(modifier = Modifier.height(4.dp))
            AuthButton(regBtnText, regBtn, "ЗАРЕГИСТРИРОВАТЬСЯ") {
                navController?.navigate("registration") ?: Log.e("NAV_CONTROLLER", "Cant navigate to registration")
            } // здесь onClick }
        }
    }
}



@Composable
fun TextMain(text: String, color: Color) = Text(
    text = text,
    fontSize = 30.sp,
    fontWeight = FontWeight.Bold,
    color = color,
    letterSpacing = 1.8.sp
)

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}