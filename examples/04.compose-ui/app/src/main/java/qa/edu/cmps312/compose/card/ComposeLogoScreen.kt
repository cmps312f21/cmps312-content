package qa.edu.cmps312.compose.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import qa.edu.cmps312.compose.R
import qa.edu.cmps312.compose.ui.theme.ComposeUITheme

@Composable
fun ComposeLogoScreen() {
    Card (elevation = 10.dp) {
        var expanded by remember { mutableStateOf(false) }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded })
        {
            Image(painter = painterResource(id = R.drawable.img_compose_logo),
                contentDescription = "Jetpack compose logo",
                modifier = Modifier.height(300.dp))
            if (expanded) {
                Text(
                    text = "Jetpack Compose",
                    style = MaterialTheme.typography.h4
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeLogoScreenPreview() {
    ComposeUITheme {
        ComposeLogoScreen()
    }
}