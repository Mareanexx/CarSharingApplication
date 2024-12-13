package ru.mareanexx.carsharing.ui.components.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.mareanexx.carsharing.ui.theme.cherry
import ru.mareanexx.carsharing.ui.theme.textRules

@Composable
fun RulesComponent() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PartTextRules("При входе и регистрации вы даете согласие на", textRules)
        PartTextRules("обработку персональных данных", cherry)
        Spacer(modifier = Modifier.height(10.dp))
        PartTextRules("Политика обработки персональных данных", cherry)
    }
}