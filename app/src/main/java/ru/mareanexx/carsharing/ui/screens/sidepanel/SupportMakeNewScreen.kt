package ru.mareanexx.carsharing.ui.screens.sidepanel

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.mareanexx.carsharing.ui.components.auth.AuthButton
import ru.mareanexx.carsharing.ui.components.sidepanel.MainTextInTitleZone
import ru.mareanexx.carsharing.ui.components.sidepanel.NotifNewRequestText
import ru.mareanexx.carsharing.ui.components.sidepanel.PreviusButton
import ru.mareanexx.carsharing.ui.components.sidepanel.SwitcherHistoryOrNew
import ru.mareanexx.carsharing.ui.theme.backgroundBtn
import ru.mareanexx.carsharing.ui.theme.black
import ru.mareanexx.carsharing.ui.theme.blackBtn
import ru.mareanexx.carsharing.ui.theme.errorLogin
import ru.mareanexx.carsharing.ui.theme.hintAndIcColor
import ru.mareanexx.carsharing.ui.theme.lineAroundTitle
import ru.mareanexx.carsharing.ui.theme.lineDivider
import ru.mareanexx.carsharing.ui.theme.successText
import ru.mareanexx.carsharing.ui.theme.textRules
import ru.mareanexx.carsharing.ui.theme.titleTextColor
import ru.mareanexx.carsharing.ui.theme.white
import ru.mareanexx.carsharing.ui.viewmodel.UserRequestsViewModel

@Composable
fun SupportMakeNewScreen(
    navController: NavController? = null,
    userRequestsViewModel: UserRequestsViewModel = viewModel(),
    idUser: Int
) {
    var messageTitle by remember { mutableStateOf("") }
    var messageText by remember { mutableStateOf("") }
    var showError by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.background(white).padding(horizontal = 20.dp).fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(58.dp).fillMaxWidth())
        PreviusButton { navController?.navigate("previous_screen") } // здесь ONCLICK
        MainTextInTitleZone("Поддержка")

        // Переключатель История и Новое
        SwitcherHistoryOrNew(
            backgroundBtn, black, black, white,
            onClickHistory = {
                navController?.navigate("support_history/$idUser")
            },
            onClickNew = {}
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Написать новое письмо
        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
        ) {
            Row(
                modifier = Modifier.padding(start = 10.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.PlayArrow, null,
                    modifier = Modifier.padding(end = 8.dp).size(20.dp),
                    tint = textRules
                )
                Text(
                    text = "Тема",
                    fontSize = 14.sp,
                    color = textRules
                )
            }
            OutlinedTextField(
                value = messageTitle,
                onValueChange = { messageTitle = it; showError = 0 },
                placeholder = {
                    Text(
                        text = "Опишите вашу проблему...", color = hintAndIcColor, fontSize = 14.sp,
                        letterSpacing = 0.5.sp, fontWeight = FontWeight.Normal
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = lineAroundTitle,
                    focusedBorderColor = lineDivider,
                    unfocusedTextColor = hintAndIcColor,
                    focusedTextColor = titleTextColor
                ),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(start = 10.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.PlayArrow, null,
                    modifier = Modifier.padding(end = 8.dp).size(20.dp),
                    tint = textRules
                )
                Text(
                    text = "Текст",
                    fontSize = 14.sp,
                    color = textRules
                )
            }
            OutlinedTextField(
                value = messageText,
                onValueChange = { messageText = it; showError = 0 },
                placeholder = {
                    Text(
                        text = "Опишите вашу проблему...", color = hintAndIcColor, fontSize = 14.sp,
                        letterSpacing = 0.5.sp, fontWeight = FontWeight.Normal
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = lineAroundTitle,
                    focusedBorderColor = lineDivider,
                    unfocusedTextColor = hintAndIcColor,
                    focusedTextColor = titleTextColor
                ),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth().height(150.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }
        // Текст ошибки или успеха или пусто (если ничего не отправлять)
        when (showError) {
            0 -> Spacer(modifier = Modifier.height(40.dp))
            1 -> NotifNewRequestText("Обращение успешно отправлено", successText)
            2 -> NotifNewRequestText("Не удалось отправить обращение", errorLogin)
            3 -> NotifNewRequestText("Поля не заполнены", errorLogin)
        }

        AuthButton(white, blackBtn, "ОТПРАВИТЬ") {
            if (messageText.isNotBlank() && messageTitle.isNotBlank()) {
                userRequestsViewModel.createNewRequest(
                    messageTitle, messageText, idUser,
                    onSuccess = {
                        Log.d("USER_REQUESTS", "Success, show UI success text")
                        showError = 1
                    },
                    onError = {
                        Log.d("USER_REQUESTS", "Error, show UI error text")
                        showError = 2
                    }
                )
            } else {
                Log.d("USER_REQUESTS", "No text provided, so show notif with no text")
                showError = 3
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewSupportScreenMakeNew() {
    SupportMakeNewScreen(idUser = 1)
}