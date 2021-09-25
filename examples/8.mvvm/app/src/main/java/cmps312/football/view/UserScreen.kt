package cmps312.football.view


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*

@Composable
fun Names() {
    var name by remember { mutableStateOf("user") }
    val names = remember { mutableStateListOf<String>() }

    Column {
        Row {
            BasicTextField(
                value = name,
                onValueChange = { name = it }
            )
            Button(onClick = { names.add(name) }) {
                Text("Add")
            }
        }
        Text("Added names:")
        Column {
            for (addedName in names) {
                Text(addedName)
            }
        }
    }
}