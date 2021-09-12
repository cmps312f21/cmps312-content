package qa.edu.cmps312.compose.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import qa.edu.cmps312.compose.ui.theme.UIComponentsTheme


@Composable
fun SwitchScreen() {
    var isDarkMode by remember { mutableStateOf(false) }
    UIComponentsTheme(isDarkMode) {
        Row(
            Modifier
                .padding(8.dp)
        ) {
            Text(
                text = "Turn on dark theme",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(8.dp)
            )
            Switch(
                checked = isDarkMode,
                onCheckedChange = { isDarkMode = it },
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .align(alignment = Alignment.CenterVertically)
            )
        }
    }
}

@Preview
@Composable
fun SwitchScreenPreview() {
    SwitchScreen()
}