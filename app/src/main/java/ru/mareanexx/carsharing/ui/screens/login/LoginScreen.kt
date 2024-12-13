package ru.mareanexx.carsharing.ui.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.mareanexx.carsharing.ui.components.auth.AuthButton
import ru.mareanexx.carsharing.ui.components.auth.ErrorLoginText
import ru.mareanexx.carsharing.ui.components.auth.InputTextWithLabel
import ru.mareanexx.carsharing.ui.components.auth.PartTextLogin
import ru.mareanexx.carsharing.ui.components.auth.PreviousButtonLogin
import ru.mareanexx.carsharing.ui.components.auth.RulesComponent
import ru.mareanexx.carsharing.ui.theme.black
import ru.mareanexx.carsharing.ui.theme.blackBtn
import ru.mareanexx.carsharing.ui.theme.cherry
import ru.mareanexx.carsharing.ui.theme.nameAboveTextField
import ru.mareanexx.carsharing.ui.theme.white
import ru.mareanexx.carsharing.ui.viewmodel.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.mareanexx.carsharing.ui.viewmodel.RentalHistoryViewModel

@Composable
fun AuthenticationScreen(
    navController: NavController? = null,
    authViewModel: AuthViewModel = viewModel()
)
{
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().background(white)) {
        Spacer(modifier = Modifier.height(58.dp).fillMaxWidth())
        PreviousButtonLogin {
            navController?.navigate("home")  ?: Log.e("NAV_CONTROLLER", "Cant navigate to home from loginScreen")
        }
        Spacer(modifier = Modifier.height(120.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            PartTextLogin(cherry, "Log")
            PartTextLogin(black, "In", modifier = Modifier.padding(start = 7.dp))
        }
        Column(
            modifier = Modifier.padding(top = 30.dp)
        ) {
            InputTextWithLabel("Почта", "Введите email", Icons.Filled.Email, KeyboardType.Email) { email = it }
            InputTextWithLabel("Пароль", "Введите пароль", Icons.Filled.Lock, KeyboardType.Password) { password = it }
            Text(
                text = "Забыли пароль?",
                color = nameAboveTextField,
                fontSize = 12.sp,
                modifier = Modifier.padding(end = 30.dp, top = 10.dp).align(Alignment.End)
            )
        }

        if (showError) {
            ErrorLoginText()
        } else {
            Spacer(modifier = Modifier.height(50.dp))
        }


        // Кнопка авторизации
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp)) {
            AuthButton(white, blackBtn, "ВОЙТИ") {
                authViewModel.login(
                    email = email,
                    password = password,
                    onSuccess = { token ->
                        Log.d("LOGIN_SUCCESS", "Token: $token")
                        showError = false

                        // ВОТ ЭТО НУЖНО ОТСЮДА УБРАТЬ. Это передача user Id.
                        // Это передача userId в rental_history экран
                        /* authViewModel.userIdValue.value?.let { userId ->
                            navController?.navigate("rental_history/$userId")
                        } */

                        // ВОТ ЭТО НУЖНО ОТСЮДА УБРАТЬ. Это передача user Id.
                        // Это передача userId в Support History экран
                        /* authViewModel.userIdValue.value?.let {
                            idUser -> navController?.navigate("support_history/$idUser")
                        } */

                        // ВОТ ЭТО НУЖНО ОТСЮДА УБРАТЬ. Это передача user Id.
                        // Это передача userId в Personal Info экран
                        authViewModel.userIdValue.value?.let {
                            idUser -> navController?.navigate("personal_info/$idUser")
                        }

                    },
                    onError = {
                        Log.e("LOGIN_ERROR", "Unauthorized")
                        showError = true
                    }
                )
            }

        }

        Spacer(modifier = Modifier.height(150.dp))
        RulesComponent()
    }
}


@Preview(showSystemUi = true)
@Composable
fun AuthPreview() {
    AuthenticationScreen()
}