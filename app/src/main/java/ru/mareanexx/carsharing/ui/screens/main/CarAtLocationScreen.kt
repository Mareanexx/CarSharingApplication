package ru.mareanexx.carsharing.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CarsAtLocationScreen(
    navController: NavController? = null,
    carViewModel: CarViewModel = viewModel(),
    idLocation: Int
) {
    val carsAtLocation by carViewModel.carsAtLocation.collectAsState()
    val loading by carViewModel.loading.collectAsState()

    LaunchedEffect(idLocation) {
        carViewModel.fetchAllAvailableCars(idLocation)
    }

    // Состояния для панели
    val coroutineScope = rememberCoroutineScope()
    var selectedCar by remember { mutableStateOf<Car?>(null) }
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden, skipHalfExpanded = true)

    // Обертка для экрана и нижней панели
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetBackgroundColor = Color.Transparent,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetContent = {
            selectedCar?.let { car ->
                CarDetailsBottomSheetContent(
                    car = car,
                    context = LocalContext.current
                ) {
                    coroutineScope.launch { sheetState.hide() }
                }
            }
        },
        scrimColor = Color.Black.copy(alpha = 0.60f) // Регулировка затемнения фона
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .background(white)
                    .padding(horizontal = 20.dp)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(58.dp).fillMaxWidth())
                PreviusButton { navController?.navigate("home_map/") }

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
                    MainLocationTitle("Район Раменки")
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
                                    coroutineScope.launch { sheetState.show() }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun CarsAtLocationScreenPreview() {
    CarsAtLocationScreen(idLocation = 1)
}