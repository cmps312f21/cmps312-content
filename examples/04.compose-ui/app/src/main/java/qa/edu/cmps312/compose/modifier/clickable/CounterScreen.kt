package qa.edu.cmps312.compose.modifier.clickable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CounterScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        var count by remember { mutableStateOf(10) }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = "-",
                modifier = Modifier.clickable {
                    count -= 1
                }
            )
            Text(text = "$count")
            Text(
                text = "+",
                modifier = Modifier.clickable {
                    count += 1
                }
            )
        }
    }
}

@Preview
@Composable
fun CounterScreenPreview() {
    CounterScreen()
}