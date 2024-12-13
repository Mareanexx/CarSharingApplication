package ru.mareanexx.carsharing.ui.components.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.carsharing.ui.theme.hintAndIcColor
import ru.mareanexx.carsharing.ui.theme.nameAboveTextField
import ru.mareanexx.carsharing.ui.theme.textField


@Composable
fun InputTextWithLabel(
    label: String,
    placeholder: String,
    iconRes: ImageVector,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 8.dp)
    ) {

        Text(
            text = label,
            fontSize = 12.sp,
            color = nameAboveTextField,
            modifier = Modifier.padding(bottom = 4.dp, start = 15.dp),
            fontWeight = FontWeight.Normal
        )

        OutlinedTextField(
            value = text,
            onValueChange = { text = it; onValueChange(it) },
            placeholder = { Text(
                text = placeholder, color = hintAndIcColor, fontSize = 14.sp,
                letterSpacing = 0.5.sp, fontWeight = FontWeight.Normal
            )},
            leadingIcon = {
                Icon(
                    modifier = Modifier.height(24.dp).width(24.dp),
                    imageVector = iconRes,
                    contentDescription = null,
                    tint = hintAndIcColor
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = textField,
                focusedBorderColor = nameAboveTextField,
                unfocusedTextColor = textField,
                focusedTextColor = nameAboveTextField
            ),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }
}
