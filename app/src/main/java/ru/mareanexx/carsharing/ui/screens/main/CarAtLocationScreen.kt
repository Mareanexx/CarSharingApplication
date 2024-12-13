package ru.mareanexx.carsharing.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.mareanexx.carsharing.R
import ru.mareanexx.carsharing.ui.components.cars.CarCard
import ru.mareanexx.carsharing.ui.components.sidepanel.MainLocationTitle
import ru.mareanexx.carsharing.ui.components.sidepanel.PreviusButton
import ru.mareanexx.carsharing.ui.theme.backgroundCarCard
import ru.mareanexx.carsharing.ui.theme.backgroundFrame
import ru.mareanexx.carsharing.ui.theme.black
import ru.mareanexx.carsharing.ui.theme.btnMinutesColor
import ru.mareanexx.carsharing.ui.theme.carNameColor
import ru.mareanexx.carsharing.ui.theme.cherry
import ru.mareanexx.carsharing.ui.theme.nameAboveTextField
import ru.mareanexx.carsharing.ui.theme.prevBtn
import ru.mareanexx.carsharing.ui.theme.textInFrame
import ru.mareanexx.carsharing.ui.theme.titleTextColor
import ru.mareanexx.carsharing.ui.theme.white

@Composable
fun CarsAtLocationScreen(
    navController: NavController? = null,
    idLocation: Int
)
{



    Column(
        modifier = Modifier.background(white).padding(horizontal = 20.dp).fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(58.dp).fillMaxWidth())
        PreviusButton { navController?.navigate("previous_screen") }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
        ) {
            Icon(
                Icons.Default.LocationOn,
                contentDescription = null,
                tint = cherry,
                modifier = Modifier.padding(end = 6.dp).size(24.dp)
            )
            MainLocationTitle("Район Раменки")
        }

        // Доступные авто + фильтры
        Column(
            modifier = Modifier.padding(start = 30.dp).fillMaxWidth()
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
                modifier = Modifier.padding(top = 10.dp).background(
                    color = prevBtn,
                    shape = RoundedCornerShape(10.dp)
                ).padding(6.dp).align(Alignment.End),
                tint = titleTextColor
            )
        }

        CarCard("Haval Jolion", "М 175 КО 797", "355 км - 80%",
            "19.63", "193.63", painterResource(id = R.drawable.havaljolion))


        CarCard("Haval Jolion", "М 175 КО 797", "355 км - 80%",
            "19.63", "193.63", painterResource(id = R.drawable.havaljolion))

    }
}

@Preview(showSystemUi = true)
@Composable
fun CarsAtLocationScreenPreview() {
    CarsAtLocationScreen(idLocation = 1)
}