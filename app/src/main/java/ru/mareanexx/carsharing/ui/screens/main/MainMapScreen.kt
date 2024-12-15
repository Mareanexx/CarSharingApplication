package ru.mareanexx.carsharing.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapType
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import ru.mareanexx.carsharing.data.models.Location
import ru.mareanexx.carsharing.ui.components.map.createBitmapFromView
import ru.mareanexx.carsharing.ui.theme.black
import ru.mareanexx.carsharing.ui.theme.cherry
import ru.mareanexx.carsharing.ui.theme.white
import ru.mareanexx.carsharing.ui.viewmodel.LocationViewModel

@Composable
fun MainMapScreen(
    navController: NavController? = null,
    locationViewModel: LocationViewModel = viewModel(),
    randomKey: Int
) {
    val locations by locationViewModel.locations.collectAsState()
    val loading by locationViewModel.loading.collectAsState()

    LaunchedEffect(randomKey) {
        locationViewModel.getAllLocations()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            YandexMap(locations)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 15.dp,
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                )
                .background(
                    color = white,
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                )
                .padding(top = 40.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Shérimobile",
                color = cherry,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }
        Column(
            modifier = Modifier.padding(top = 100.dp, start = 20.dp)
        ) {
            Icon(
                Icons.Default.Menu,
                contentDescription = "Menu",
                tint = black,
                modifier = Modifier.size(40.dp).shadow(7.dp, RoundedCornerShape(10.dp)).background(
                    color = white,
                    shape = RoundedCornerShape(10.dp)
                ).padding(horizontal = 7.dp)
            )
        }

    }
}

@Composable
fun YandexMap(locations: List<Location>) {
    val currentContext = LocalContext.current
    val mapView = remember {
        MapView(currentContext)
    }

    DisposableEffect(Unit) {
        MapKitFactory.getInstance().onStart()
        mapView.onStart()

        onDispose {
            mapView.onStop()
            MapKitFactory.getInstance().onStop()
        }
    }

    AndroidView(
        factory = { mapView.apply {
            mapWindow.map.mapType = MapType.MAP
            mapWindow.map.move(
                CameraPosition(Point(55.7006880, 37.5088020), 15.0f, 0.0f, 0.0f),
                Animation(Animation.Type.SMOOTH, 0f),
                null
            )
            for (location in locations) {
                val bitmap = createBitmapFromView(context, location.name)
                // метка с иконкой и текстом
                mapWindow.map.mapObjects.addPlacemark().apply {
                    geometry = Point(location.latitude.toDouble(), location.longitude.toDouble())
                    setIcon(ImageProvider.fromBitmap(bitmap))
                }
            }

        }},
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showSystemUi = true)
@Composable
fun PreviewMainMap() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(white)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 15.dp,
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                )
                .background(
                    color = white,
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                )
                .padding(top = 40.dp, bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Shérimobile",
                color = cherry,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }
        Column(
            modifier = Modifier.padding(top = 100.dp, start = 20.dp)
        ) {
            Icon(
                Icons.Default.Menu,
                contentDescription = "Menu",
                tint = black,
                modifier = Modifier.size(40.dp).shadow(7.dp, RoundedCornerShape(10.dp)).background(
                    color = white,
                    shape = RoundedCornerShape(10.dp)
                ).padding(horizontal = 7.dp)
            )
        }
    }
}