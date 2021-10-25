package cmps312.navigation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cmps312.navigation.ui.theme.NavigationTheme


@Composable
fun AlertDialogScreen() {
    Column(Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        var isDialogOpen by remember { mutableStateOf(false) }
        var dialogResult by remember { mutableStateOf(false) }

        Text("isDialogOpen: $isDialogOpen - Dialog Result: $dialogResult")

        Button(onClick = {
            isDialogOpen = true
        }) {
            Text("Show Dialog Alert")
        }

        cmps312.navigation.ui.components.AlertDialog(isDialogOpen,
            onDialogOpenChange = { isDialogOpen = it },
            title = "Discard draft?",
            message = "This will permanently delete the current e-mail draft.",
            onDialogResult = {
                dialogResult = it
                isDialogOpen = false
            }
        )
    }
}

@Preview
@Composable
fun AlertDialogScreenPreview() {
    NavigationTheme {
        AlertDialogScreen()
    }
}