package ru.mareanexx.carsharing.ui.components.sidepanel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mareanexx.carsharing.R
import ru.mareanexx.carsharing.ui.theme.prevBtn


@Composable
fun PreviusButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.height(34.dp).width(34.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = prevBtn
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Image(
            modifier = Modifier.width(11.dp),
            painter = painterResource(id = R.drawable.arrow_back_1),
            contentDescription = "Стрелка назад"
        )
    }
}


@Preview
@Composable
fun PreviousButtonLoginPreview() {
    PreviusButton {
        println("nothing")
    }
}