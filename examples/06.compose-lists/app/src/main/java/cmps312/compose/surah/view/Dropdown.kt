package cmps312.compose.surah.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

@Composable
fun Dropdown(
    options: List<String>, selectedOption: String, onSelectionChange: (String)-> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    //Box(Modifier.fillMaxWidth().wrapContentSize(Alignment.TopEnd)) {
    Box(modifier = modifier) {
    //Column {
       IconButton(onClick = { expanded = !expanded }, modifier = Modifier.fillMaxWidth()) {
           Row {
               Text(selectedOption)
               Icon(
                   imageVector = Icons.Filled.ArrowDropDown,
                   contentDescription = null,
               )
           }
        }
        // Opens a popup with the given content
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