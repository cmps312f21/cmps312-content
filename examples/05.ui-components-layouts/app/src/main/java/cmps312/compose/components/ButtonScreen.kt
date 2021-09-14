package cmps312.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmps312.compose.R
import cmps312.compose.ui.theme.AppTheme

@Composable
fun ButtonScreen() {
    var message by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.padding(10.dp)
    ) {
        Text(text = message)
        Button(onClick = { message = "Button clicked" }) {
            Text("Button")
        }

        OutlinedButton(onClick = { message = "OutlinedButton clicked" }) {
            Text("OutlinedButton")
        }

        TextButton(onClick = { message = "TextButton clicked" }) {
            Text("TextButton")
        }

        // Search for icons @ https://fonts.google.com/icons
        IconButton(onClick = { message = "Search IconButton clicked" }) {
            Icon(
                Icons.Outlined.Search,
                contentDescription = "Search",
            )
        }

        IconButton(onClick = { message = "Quran IconButton clicked" }) {
            Icon(painterResource(id = R.drawable.ic_quran), "Quran")
        }
    }
}

@Preview
@Composable
fun ButtonScreenPreview() {
    AppTheme {
        ButtonScreen()
    }
}