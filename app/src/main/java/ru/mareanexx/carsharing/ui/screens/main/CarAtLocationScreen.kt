package ru.mareanexx.carsharing.ui.screens.main

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.mareanexx.carsharing.data.models.Car
import ru.mareanexx.carsharing.ui.components.cars.CarCard
import ru.mareanexx.carsharing.ui.components.cars.CarDetailsBottomSheetContent
import ru.mareanexx.carsharing.ui.components.sidepanel.MainLocationTitle
import ru.mareanexx.carsharing.ui.components.sidepanel.PreviusButton
import ru.mareanexx.carsharing.ui.theme.cherry
import ru.mareanexx.carsharing.ui.theme.nameAboveTextField
import ru.mareanexx.carsharing.ui.theme.prevBtn
import ru.mareanexx.carsharing.ui.theme.titleTextColor
import ru.mareanexx.carsharing.ui.theme.white
import ru.mareanexx.carsharing.ui.viewmodel.CarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarsAtLocationScreen(
    navController: NavController,
    carViewModel: CarViewModel = viewModel(),
    idLocation: Int,
    idUser: Int,
    title: String
) {
    val carsAtLocation by carViewModel.carsAtLocation.collectAsState()
    val loading by carViewModel.loading.collectAsState()

    LaunchedEffect(idLocation) {
        carViewModel.fetchAllAvailableCars(idLocation)
    }

    // Состояния для панели
    val coroutineScope = rememberCoroutineScope()
    var selectedCar by remember { mutableStateOf<Car?>(null) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showBottomSheet by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .background(white)
            .padding(horizontal = 20.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(58.dp).fillMaxWidth())
        PreviusButton {
            Log.d("CARS", "Хочу перенаправить на другой экран home_map/$idUser")
            navController.navigate("home_map/$idUser")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Icon(
                Icons.Default.LocationOn,
                contentDescription = null,
                tint = cherry,
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(24.dp)
            )
            MainLocationTitle(title)
        }

        Column(
            modifier = Modifier
                .padding(start = 30.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Доступные авто",
                color = nameAboveTextField,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                letterSpacing = 0.5.sp
            )
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .background(
                        color = prevBtn,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(6.dp)
                    .align(Alignment.End),
                tint = titleTextColor
            )
        }

        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            if (carsAtLocation.isEmpty()) {
                Text(
                    text = "Нет доступных автомобилей для аренды",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(carsAtLocation) { car ->
                        CarCard(car, LocalContext.current) {
                            selectedCar = car
                            showBottomSheet = true
                        }
                    }
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            scrimColor = titleTextColor,
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            containerColor = white,
            dragHandle = null,
            sheetState = sheetState
        ) {
            selectedCar?.let { car ->
                CarDetailsBottomSheetContent(
                    car = car,
                    idUser = idUser,
                    context = LocalContext.current,
                    navController = navController,
                ) {
                    coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }
            }
        }
    }
}


//@Preview(showSystemUi = true)
//@Composable
//fun CarsAtLocationScreenPreview() {
//    CarsAtLocationScreen(idLocation = 1, idUser = 1, title = "Локация")
//}