package cmps312.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmps312.compose.ui.theme.AppTheme

@Composable
fun SwitchScreen() {
    var isDarkMode by remember { mutableStateOf(false) }
    AppTheme(darkTheme = isDarkMode) {
        Row(
            Modifier.background(MaterialTheme.colors.background)
                    .fillMaxSize()
        ) {
            Text(
                text = "Turn on dark theme",
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colors.primary
            )
            Switch(
                checked = isDarkMode,
                onCheckedChange = { isDarkMode = it },
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}

@Preview
@Composable
fun SwitchScreenPreview() {
    SwitchScreen()
}