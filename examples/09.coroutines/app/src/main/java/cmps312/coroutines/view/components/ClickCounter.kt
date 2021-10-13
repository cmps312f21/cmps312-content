package cmps312.coroutines.view.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun ClickCounter(modifier: Modifier = Modifier) {
    var clicksCount by remember { mutableStateOf(0) }
    Button(
        modifier = modifier,
        onClick = { clicksCount++ }
    ) {
        Text("Clicked $clicksCount times")
    }
}