package ru.mareanexx.carsharing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.yandex.mapkit.MapKitFactory
import ru.mareanexx.carsharing.ui.AppNavHost


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        MapKitFactory.setApiKey("d601d06a-350d-45a2-8054-c1e0749fb647")
        MapKitFactory.setLocale("ru_RU")
        MapKitFactory.initialize(this)

        setContent {
            val navController = rememberNavController()
            AppNavHost(navController)
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun Preview() {
}