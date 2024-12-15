package ru.mareanexx.carsharing.ui.components.navigation

import android.graphics.fonts.FontStyle
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.mareanexx.carsharing.R
import ru.mareanexx.carsharing.ui.theme.cherry
import ru.mareanexx.carsharing.ui.theme.navigationText
import ru.mareanexx.carsharing.ui.theme.titleTextColor
import ru.mareanexx.carsharing.ui.theme.white

@Composable
fun MainNavigationPanel(
    navController: NavController? = null,
    idUser: Int
) {
    Column(
        modifier = Modifier.fillMaxHeight().width(310.dp).background(white)
    ) {
        Image(
            painter = painterResource(id = R.drawable.header),
            contentDescription = "header",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "Shérimobile",
            color = cherry,
            fontSize = 36.sp,
            letterSpacing = 1.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(bottom = 30.dp, top = 10.dp),
            textAlign = TextAlign.Center
        )
        Column(
            modifier = Modifier.padding(horizontal = 45.dp, vertical = 30.dp).fillMaxWidth()
        ) {
            RowNavigation("ЛИЧНЫЕ ДАННЫЕ", Icons.Default.Person) { navController?.navigate("personal_info/$idUser") } // OnClikc переход на другой экран
            RowNavigation("ПОЕЗДКИ", Icons.Default.LocationOn) { navController?.navigate("rental_history/$idUser") } // OnClikc переход на другой экран
            RowNavigation("БОНУСЫ", Icons.Default.Favorite) {}
            RowNavigation("НАСТРОЙКИ", Icons.Default.Settings) {}
            RowNavigation("ПОДДЕРЖКА", Icons.Default.Info) { navController?.navigate("support_history/$idUser") } // OnClikc переход на другой экран
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "о приложении",
            fontSize = 14.sp,
            color = navigationText,
            letterSpacing = 0.5.sp,
            modifier = Modifier.padding(start = 30.dp, bottom = 30.dp)
        )
    }
}

@Composable
fun RowNavigation(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 20.dp).fillMaxWidth()
            .clickable { onClick() }
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = icon,
            contentDescription = "icon",
            tint = titleTextColor
        )
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp,
            lineHeight = 38.sp,
            color = navigationText,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewMainNavigationPanel() {
    MainNavigationPanel(idUser = 1)
}
