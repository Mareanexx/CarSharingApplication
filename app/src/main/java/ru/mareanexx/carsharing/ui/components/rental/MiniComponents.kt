package ru.mareanexx.carsharing.ui.components.rental

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.carsharing.ui.theme.black
import ru.mareanexx.carsharing.ui.theme.titleTextColor
import ru.mareanexx.carsharing.ui.theme.valueTextColor
import ru.mareanexx.carsharing.ui.theme.white


@Composable
fun TempRentalBlock(
    carName: String,
    modifier: Modifier = Modifier,
    onOpen: () -> Unit
) {
    Row(
        modifier = modifier.padding(horizontal = 15.dp, vertical = 40.dp).fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(15.dp)
            )
            .background(
                color = white,
                shape = RoundedCornerShape(15.dp)
            ).padding(start = 25.dp, top = 15.dp, end = 10.dp, bottom = 15.dp)
            .clickable {
                onOpen()
            },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "В пути",
                fontWeight = FontWeight.Medium,
                fontSize = 26.sp,
                color = black
            )
            Text(
                text = carName,
                fontSize = 15.sp,
                color = titleTextColor
            )
        }
        IconButton(
            onClick = {}
        ) {
            Icon(
                Icons.Default.KeyboardArrowUp,
                contentDescription = null,
                modifier = Modifier.size(34.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewMiniComponents() {
    Box(
        modifier = Modifier.padding(top = 50.dp).fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        TempRentalBlock("Haval Jolion") {}
    }
}