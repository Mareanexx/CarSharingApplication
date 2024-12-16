package ru.mareanexx.carsharing.ui.screens.sidepanel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.mareanexx.carsharing.R
import ru.mareanexx.carsharing.ui.components.sidepanel.MainTextInTitleZone
import ru.mareanexx.carsharing.ui.components.sidepanel.PersonalInfoRowOfInfo
import ru.mareanexx.carsharing.ui.components.sidepanel.PreviusButton
import ru.mareanexx.carsharing.ui.theme.backgroundBlock
import ru.mareanexx.carsharing.ui.theme.lineDivider
import ru.mareanexx.carsharing.ui.theme.roundNumberColor
import ru.mareanexx.carsharing.ui.theme.titleTextColor
import ru.mareanexx.carsharing.ui.theme.valueTextColor
import ru.mareanexx.carsharing.ui.theme.white
import ru.mareanexx.carsharing.ui.viewmodel.PersonalInfoViewModel
import ru.mareanexx.carsharing.utils.UserStore

@Composable
fun PersonalInfoScreen(
    navController: NavController? = null,
    personalInfoViewModel: PersonalInfoViewModel = viewModel(),
    userStore: UserStore,
    idUser: Int
) {
    val coroutineScope = rememberCoroutineScope()
    val personalInfo by personalInfoViewModel.personalInfo.collectAsState()
    val loading by personalInfoViewModel.loading.collectAsState()

    LaunchedEffect(idUser) {
        personalInfoViewModel.fetchPersonalInfo(
            idUser = idUser
        )
    }

    Column(
        modifier = Modifier.background(white).padding(horizontal = 20.dp).fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(58.dp).fillMaxWidth())
        PreviusButton { navController?.navigate("home_map/$idUser") } // здесь ONCLICK
        MainTextInTitleZone("Личные данные")

        // блок с личной инфой
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            Column(
                modifier = Modifier.background(
                    color = backgroundBlock,
                    shape = RoundedCornerShape(16.dp)
                ).padding(20.dp).fillMaxWidth()
            ) {
                PersonalInfoRowOfInfo(Icons.Default.Email, "E-mail", personalInfo.email)
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().padding(
                        vertical = 20.dp,
                        horizontal = 4.dp
                    ),
                    thickness = 1.dp,
                    color = lineDivider
                )
                PersonalInfoRowOfInfo(
                    Icons.Default.Person,
                    "Имя пользователя",
                    personalInfo.username
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().padding(
                        vertical = 20.dp,
                        horizontal = 4.dp
                    ),
                    thickness = 1.dp,
                    color = lineDivider
                )
                PersonalInfoRowOfInfo(
                    Icons.Default.Phone,
                    "Номер телефона",
                    personalInfo.phoneNumber
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))

        // Блок с документами
        DocumentComponent()

        Spacer(modifier = Modifier.height(15.dp))

        // Блок выйти из аккаунта и удалить
        Column(
            modifier = Modifier.background(
                color = backgroundBlock,
                shape = RoundedCornerShape(16.dp)
            ).padding(horizontal = 20.dp, vertical = 21.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Выйти из аккаунта",
                color = titleTextColor,
                fontSize = 16.sp,
                letterSpacing = 0.1.sp,
                modifier = Modifier.padding(start = 6.dp).clickable {
                    coroutineScope.launch {
                        userStore.clearUser()
                    }
                    navController?.navigate("home") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp,
                    vertical = 20.dp),
                thickness = 1.dp,
                color = lineDivider
            )
            Text(
                text = "Удалить аккаунт",
                color = roundNumberColor,
                fontSize = 16.sp,
                letterSpacing = 0.1.sp,
                modifier = Modifier.padding(start = 6.dp)
            )
        }

    }
}

@Composable
fun DocumentComponent() {
    Row(
        modifier = Modifier.background(
            color = backgroundBlock,
            shape = RoundedCornerShape(16.dp)
        ).padding(20.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = valueTextColor,
            modifier = Modifier.height(30.dp).width(30.dp)
        )
        Text(
            text = "Документы",
            color = titleTextColor,
            fontSize = 16.sp,
            letterSpacing = 0.1.sp,
            modifier = Modifier.padding(start = 15.dp, end = 10.dp)
        )
        // цифра в круге
        Text(
            text = "2",
            modifier = Modifier.background(
                color = roundNumberColor,
                shape = RoundedCornerShape(20.dp)
            ).padding(horizontal = 7.dp, vertical = 3.dp),
            color = white
        )
        // Промежуток, который "толкает" Icon к правому краю
        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.arrow_back_1),
            contentDescription = null,
            tint = titleTextColor,
            modifier = Modifier.size(16.dp).graphicsLayer(
                rotationZ = 180f
            )
        )
    }
}