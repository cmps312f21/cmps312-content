package cmps312.navigation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import cmps312.navigation.ui.Screen

@Composable
fun SearchScreen() {
        ScreenContent(content = Screen.Search.title, icon = Screen.Search.icon)
}

@Composable
fun SettingsScreen() {
    ScreenContent(content = Screen.Settings.title, icon = Screen.Settings.icon)
}

@Composable
fun ScreenContent(content: String, icon: ImageVector?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        icon?.let {
            Icon(
                imageVector = icon,
                contentDescription = content,
                tint = MaterialTheme.colors.primarySurface
            )
        }
        Text(text = content)
    }
}