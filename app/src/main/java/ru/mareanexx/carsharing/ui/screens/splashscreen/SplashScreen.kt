package ru.mareanexx.carsharing.ui.screens.splashscreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.mareanexx.carsharing.R
import ru.mareanexx.carsharing.ui.theme.white
import ru.mareanexx.carsharing.utils.UserStore

@Composable
fun SplashScreen(
    navController: NavController,
    userStore: UserStore
) {
    val coroutineScope = rememberCoroutineScope()
    val isLoggedIn = userStore.isLoggedIn.collectAsState(initial = false)
    val idUser = userStore.userId.collectAsState(initial = 0)

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            delay(2000)
            if (isLoggedIn.value) {
                Log.d("STORE", "User existed with id = ${idUser.value}, ${isLoggedIn.value}")
                // Пользователь авторизован
                navController.navigate("home_map/${idUser.value}") {
                    popUpTo("splash") { inclusive = true }
                }
            } else {
                Log.d("STORE", "User doesnt exist with id = $idUser, $isLoggedIn")
                // Пользователь не авторизован
                navController.navigate("home") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {
        Image(
            painter = painterResource(id = R.drawable.header2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Image(
            painter = painterResource(id = R.drawable.sherimobile),
            contentDescription = "title",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .fillMaxWidth()
                .align(Alignment.Center)
        )
    }
}
//
//@Preview(showSystemUi = true)
//@Composable
//fun PreviewSplashScreen() {
//    SplashScreen()
//}