package qa.edu.cmps312.compose.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun JetpackComposeCard() {
    Card {

        var expanded by remember { mutableStateOf(false) }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { expanded = !expanded   })
        {
            Image(painter =
            painterResource(id = R.drawable.img_compose_logo),
                contentDescription = "Jetpack compose logo",
                modifier = Modifier.sizeIn(maxHeight = 300.dp))
            if (expanded) {
                Text(
                    text = "Jetpack Compose",
                    style = MaterialTheme.typography.h4,
                )
            }
        }
    }
}