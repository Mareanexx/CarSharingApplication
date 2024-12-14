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
import ru.mareanexx.carsharing.ui.components.sidepanel.RentalHistoryElement
import ru.mareanexx.carsharing.ui.theme.white
import ru.mareanexx.carsharing.ui.viewmodel.RentalHistoryViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun RentalHistoryScreen(
    navController: NavController? = null,
    rentalHistoryViewModel: RentalHistoryViewModel = viewModel(),
    userId: Int
) {
    val rentalHistory by rentalHistoryViewModel.rentalHistory.collectAsState()
    val loading by rentalHistoryViewModel.loading.collectAsState()

    // триггер
    LaunchedEffect(userId) {
        rentalHistoryViewModel.fetchRentalHistory(
            userId = userId,
            onError = {  }
        )
    }

    Column(
        modifier = Modifier.background(white).padding(horizontal = 20.dp).fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(58.dp).fillMaxWidth())
        PreviusButton { navController?.navigate("previous_screen") }

        MainTextInTitleZone("История поездок")

        if (loading) {
            Log.d("UI_STATE", "Loading: $loading, Rental history size: ${rentalHistory.size}")
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            Log.d("UI_STATE", "Loading: $loading, Rental history size: ${rentalHistory.size}")
            Log.d("RENTAL_HISTORY_UI", "Rendering list with size: ${rentalHistory.size}")
            if (rentalHistory.isEmpty()) {
                Text(
                    text = "История поездок пуста",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(rentalHistory) { rental ->
                        Log.d("RENTAL_ITEM", "Rental: $rental")
                        RentalHistoryElement(
                            date = rental.startTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm", Locale("ru"))),
                            carName = "${rental.brand} ${rental.model}",
                            price = "${rental.totalPrice} ₽"
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewHistoryScreen() {
    RentalHistoryScreen(userId = 1)
}