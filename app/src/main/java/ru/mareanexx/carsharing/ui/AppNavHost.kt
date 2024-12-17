package ru.mareanexx.carsharing.ui

import androidx.compose.runtime.Composable
import androidx.datastore.preferences.core.PreferencesSerializer.defaultValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.mareanexx.carsharing.ui.screens.login.AuthenticationScreen
import ru.mareanexx.carsharing.ui.screens.login.HomeScreen
import ru.mareanexx.carsharing.ui.screens.login.RegistrationScreen
import ru.mareanexx.carsharing.ui.screens.main.CarsAtLocationScreen
import ru.mareanexx.carsharing.ui.screens.main.MainMapScreen
import ru.mareanexx.carsharing.ui.screens.sidepanel.PersonalInfoScreen
import ru.mareanexx.carsharing.ui.screens.sidepanel.RentalHistoryScreen
import ru.mareanexx.carsharing.ui.screens.sidepanel.SupportHistoryScreen
import ru.mareanexx.carsharing.ui.screens.sidepanel.SupportMakeNewScreen
import ru.mareanexx.carsharing.ui.screens.splashscreen.SplashScreen
import ru.mareanexx.carsharing.utils.UserStore

@Composable
fun AppNavHost(navController: NavHostController, userStore: UserStore) {

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") { SplashScreen(navController, userStore) }
        composable("home") { HomeScreen(navController) }
        composable("login") { AuthenticationScreen(navController, userStore) }
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
            PersonalInfoScreen(navController, idUser = idUser, userStore = userStore)
        }
        composable(
            "loc-{idLocation}/cars/{idUser}?title={title}",
            arguments = listOf(
                navArgument("idLocation") { type = NavType.IntType },
                navArgument("idUser") { type = NavType.IntType },
                navArgument("title") { type = NavType.StringType; defaultValue = "Локация" }
            )
        ) { backStackEntry ->
            val idLocation = backStackEntry.arguments?.getInt("idLocation") ?: 0
            val idUser = backStackEntry.arguments?.getInt("idUser") ?: 0
            val title = backStackEntry.arguments?.getString("title") ?: "Локация"

            CarsAtLocationScreen(
                navController = navController,
                idLocation = idLocation,
                idUser = idUser,
                title = title
            )
        }
    }
}