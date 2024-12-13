package ru.mareanexx.carsharing.ui.screens.sidepanel

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.mareanexx.carsharing.ui.components.sidepanel.MainTextInTitleZone
import ru.mareanexx.carsharing.ui.components.sidepanel.PreviusButton
import ru.mareanexx.carsharing.ui.components.sidepanel.SwitcherHistoryOrNew
import ru.mareanexx.carsharing.ui.components.sidepanel.UserRequestItemBlock
import ru.mareanexx.carsharing.ui.theme.backgroundBtn
import ru.mareanexx.carsharing.ui.theme.black
import ru.mareanexx.carsharing.ui.theme.white
import ru.mareanexx.carsharing.ui.viewmodel.UserRequestsViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun SupportHistoryScreen(
    navController: NavController? = null,
    userRequestsViewModel: UserRequestsViewModel = viewModel(),
    idUser: Int
) {
    val userRequests by userRequestsViewModel.userRequests.collectAsState()
    val loading by userRequestsViewModel.loading.collectAsState()

    LaunchedEffect(idUser) {
        userRequestsViewModel.fetchUserRequests(
            idUser = idUser,
            onError = {}
        )
    }

    Column(
        modifier = Modifier.background(white).padding(horizontal = 20.dp).fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(58.dp).fillMaxWidth())
        PreviusButton { navController?.navigate("previous_screen") } // здесь ONCLICK
        MainTextInTitleZone("Поддержка")

        // Переключатель История и Новое
        SwitcherHistoryOrNew(
            black,backgroundBtn, white, black,
            onClickHistory = {},
            onClickNew = {
                navController?.navigate("support_new/$idUser")
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (loading) {
            Log.d("UI_STATE", "Loading: $loading, User Requests size: ${userRequests.size}")
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            Log.d("USER_REQUESTS_UI", "Loading: $loading, User Requests size: ${userRequests.size}")
            if (userRequests.isEmpty()) {
                Text(
                    text = "История обращений пуста",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(userRequests) { request ->
                        Log.d("REQUEST_ITEM", "Request: $request")
                        UserRequestItemBlock(
                            themeTitle = request.messageTitle,
                            date = request.createdAt.format(DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", Locale("ru"))),
                            status = if (request.status == "OPEN") "На рассмотрении" else "Решено",
                            requestText = request.message
                        )
                    }
                }
            }
        }

//        UserRequestItemBlock(
//            themeTitle = "Проблемы с доступом",
//            date = "04 декабря 2024, 14:21",
//            status = "Решено",
//            requestText = "Здравствуйте! Не удалось забронировать авто, хотя оно было свободно, помогите!"
//        )
//
//        UserRequestItemBlock(
//            themeTitle = "Проблемы с доступом",
//            date = "04 декабря 2024, 14:21",
//            status = "Решено",
//            requestText = "Здравствуйте! Не удалось забронировать авто, хотя оно было свободно, помогите!"
//        )

        // Блок с одним обращением ЗАКРЫТЫМ
/*        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp).background(
                color = backgroundBlock,
                shape = RoundedCornerShape(15.dp)
            ).padding(7.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().background(
                    color = backgroundTheme,
                    shape = RoundedCornerShape(13.dp)
                ).padding(horizontal = 15.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Тема",
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(end = 20.dp)
                )
                Text(
                    text = "Автомобиль был не заправлен",
                    color = titleTextColor
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
            }
        } */

        // Блок с ОТКРЫТЫМ ОБРАЩЕНИЕМ
/*        Column(
            modifier = Modifier.fillMaxWidth().background(
                color = backgroundBlock,
                shape = RoundedCornerShape(15.dp)
            ).padding(7.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().background(
                    color = backgroundTheme,
                    shape = RoundedCornerShape(13.dp)
                ).padding(horizontal = 15.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Тема",
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(end = 20.dp)
                )
                Text(
                    text = "Проблемы с доступом",
                    color = titleTextColor
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
            }
            // Контент
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
                    text = "04 декабря 2024, 14:21",
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
                    text = "Решено",
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
                    text = "Здравствуйте! Не удалось забронировать авто, хотя оно было свободно, помогите!",
                    color = titleTextColor
                )
            }
        } */
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewSupportScreen() {
    SupportHistoryScreen(idUser = 1)
}