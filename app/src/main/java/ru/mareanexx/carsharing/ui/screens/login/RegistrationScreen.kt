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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.mareanexx.carsharing.ui.components.auth.AuthButton
import ru.mareanexx.carsharing.ui.components.auth.ErrorRegText
import ru.mareanexx.carsharing.ui.components.auth.InputTextWithLabel
import ru.mareanexx.carsharing.ui.components.auth.PartTextLogin
import ru.mareanexx.carsharing.ui.components.auth.PreviousButtonLogin
import ru.mareanexx.carsharing.ui.components.auth.RulesComponent
import ru.mareanexx.carsharing.ui.theme.black
import ru.mareanexx.carsharing.ui.theme.blackBtn
import ru.mareanexx.carsharing.ui.theme.cherry
import ru.mareanexx.carsharing.ui.theme.white
import ru.mareanexx.carsharing.ui.viewmodel.AuthViewModel

@Composable
fun RegistrationScreen(
    navController: NavController? = null,
    authViewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().background(white)) {
        Spacer(modifier = Modifier.height(58.dp).fillMaxWidth())
        PreviousButtonLogin {
            navController?.navigate("home") ?: Log.e("NAV_CONTROLLER", "Cant navigate to home from registrScreen")
        }
        Spacer(modifier = Modifier.height(60.dp))

        // Sign Up text
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            PartTextLogin(cherry, "Sign")
            PartTextLogin(black, "Up", modifier = Modifier.padding(start = 7.dp))
        }

        // form Sign Up
        Column(
            modifier = Modifier.padding(top = 30.dp)
        ) {
            InputTextWithLabel("Имя пользователя", "Введите никнейм", Icons.Filled.Person, KeyboardType.Text) { userName = it }
            InputTextWithLabel("Почта", "Введите email", Icons.Filled.Email, KeyboardType.Email) { email = it }
            InputTextWithLabel("Номер телефона", "Введите номер", Icons.Filled.Phone, KeyboardType.Phone) { phoneNumber = it }
            InputTextWithLabel("Пароль", "Введите пароль", Icons.Filled.Lock, KeyboardType.Password) { password = it }
        }

        if (showError) {
            ErrorRegText()
        } else {
            Spacer(modifier = Modifier.height(30.dp))
        }

        // reg button
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp)
        ) {
            AuthButton(white, blackBtn, "ЗАРЕГИСТРИРОВАТЬСЯ") {
                authViewModel.register(
                    username = userName,
                    email = email,
                    phoneNumber = phoneNumber,
                    password = password,
                    onSuccess = {
                        navController?.navigate("login") ?: Log.e("NAV_CONTROLLER", "Can't navigate to login screen")
                    },
                    onError = {
                        showError = true
                    }
                )
            }
        }

        // rules
        Spacer(modifier = Modifier.height(75.dp))
        RulesComponent()
    }
}


@Preview(showSystemUi = true)
@Composable
fun RegistrPreview() {
    RegistrationScreen()
}