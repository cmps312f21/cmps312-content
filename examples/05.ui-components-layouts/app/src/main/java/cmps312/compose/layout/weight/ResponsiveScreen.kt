package cmps312.compose.layout.weight

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ResponsiveScreen() {
    Column(
        Modifier
            .background(Color(0xFFEDEAE0))
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0XFF8DB600))
                .weight(1F)
        )
        Row(Modifier.weight(8F).fillMaxWidth()) {
            Box(
                Modifier
                    .weight(3f)
                    .background(Color.Blue)
                    .fillMaxHeight()
                )
            Box(
                Modifier
                    .weight(1f)
                    .background(Color.Red)
                    .fillMaxHeight()
                )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0XFF4B5320))
                .weight(1F)
        )
    }
}

@Preview
@Composable
fun ResponsiveScreenPreview() {
    ResponsiveScreen()
}
