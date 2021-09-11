package qa.edu.cmps312.compose.widgets.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import qa.edu.cmps312.compose.R
import qa.edu.cmps312.compose.modifier.clickable.CounterScreen
import qa.edu.cmps312.compose.ui.theme.ComposeUITheme

@Composable
fun ButtonScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.padding(10.dp)
    ) {
        Button(onClick = {}) {
            Text("Button")
        }

        OutlinedButton(onClick = {}) {
            Text("OutlinedButton")
        }

        TextButton(onClick = {}) {
            Text("TextButton")
        }

        // Search for icons @ https://fonts.google.com/icons
        IconButton(onClick = { }) {
            Icon(
                Icons.Outlined.Search,
                contentDescription = "Search",
            )
        }

        IconButton(onClick = { }) {
            Icon(painterResource(id = R.drawable.ic_quran), "Quran")
        }
    }
}

@Preview
@Composable
fun ButtonScreenPreview() {
    ComposeUITheme {
        ButtonScreen()
    }
}