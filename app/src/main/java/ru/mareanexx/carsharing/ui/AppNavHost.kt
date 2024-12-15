package ru.mareanexx.carsharing.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.mareanexx.carsharing.ui.screens.login.HomeScreen
import ru.mareanexx.carsharing.ui.screens.login.AuthenticationScreen
import ru.mareanexx.carsharing.ui.screens.login.RegistrationScreen
import ru.mareanexx.carsharing.ui.screens.main.CarsAtLocationScreen
import ru.mareanexx.carsharing.ui.screens.main.MainMapScreen
import ru.mareanexx.carsharing.ui.screens.sidepanel.PersonalInfoScreen
import ru.mareanexx.carsharing.ui.screens.sidepanel.RentalHistoryScreen
import ru.mareanexx.carsharing.ui.screens.sidepanel.SupportHistoryScreen
import ru.mareanexx.carsharing.ui.screens.sidepanel.SupportMakeNewScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController) }
        composable("login") { AuthenticationScreen(navController) }
        composable("registration") { RegistrationScreen(navController) }
        composable("home_map/{idUser}") { backStackEntry ->
            val idUser = backStackEntry.arguments?.getString("idUser")?.toIntOrNull() ?: return@composable
            MainMapScreen(navController = navController, idUser = idUser)
        }
        composable("rental_history/{idUser}") { backStackEntry ->
            val idUser = backStackEntry.arguments?.getString("idUser")?.toIntOrNull() ?: return@composable
            RentalHistoryScreen(navController = navController, idUser = idUser)
        }
        composable("support_history/{idUser}") { backStackEntry ->
            val idUser = backStackEntry.arguments?.getString("idUser")?.toIntOrNull() ?: return@composable
            SupportHistoryScreen(navController = navController, idUser = idUser)
        }
        composable("support_new/{idUser}") { backStackEntry ->
            val idUser = backStackEntry.arguments?.getString("idUser")?.toIntOrNull() ?: return@composable
            SupportMakeNewScreen(navController = navController, idUser = idUser)
        }
        composable("personal_info/{idUser}") { backStackEntry ->
            val idUser = backStackEntry.arguments?.getString("idUser")?.toIntOrNull() ?: return@composable
            PersonalInfoScreen(navController, idUser = idUser)
        }
        composable("loc-{idLocation}/cars/{idUser}") { backStackEntry ->
            val idLocation = backStackEntry.arguments?.getString("idLocation")?.toIntOrNull() ?: return@composable
            val idUser = backStackEntry.arguments?.getString("idUser")?.toIntOrNull() ?: return@composable
            CarsAtLocationScreen(navController, idLocation = idLocation, idUser = idUser)
        }
    }
}