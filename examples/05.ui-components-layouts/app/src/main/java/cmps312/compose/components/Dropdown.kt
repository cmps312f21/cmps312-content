package cmps312.compose.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.window.Popup

@Composable
fun Dropdown(options: List<String>, selectedOption: String, onSelectionChange: (String)-> Unit) {
    var expanded by remember { mutableStateOf(false) }

    TextButton(onClick = { expanded = !expanded }){
        Text (selectedOption)
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = null,
        )
    }
    // Opens a popup with the given content
    Popup {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onSelectionChange(option)
                }) {
                    Text(text = option)
                }
            }
        }
    }
}