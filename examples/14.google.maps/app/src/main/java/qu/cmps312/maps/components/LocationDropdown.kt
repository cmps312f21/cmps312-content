package qu.cmps312.maps.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import qu.cmps312.maps.entity.Location

@Composable
fun LocationDropdown(
    label: String,
    options: List<Location>,
    selectedOption: Location,
    onSelectionChange: (Location)-> Unit) {

    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val trailingIcon = if (expanded)
        // It requires adding this dependency: implementation "androidx.compose.material:material-icons-extended:$compose_version"
        Icons.Filled.ArrowDropUp
    else
        Icons.Filled.ArrowDropDown

    Column {
        OutlinedTextField(
            value = selectedOption.toString(),
            onValueChange = {  },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textFieldSize = coordinates.size.toSize()
                },
            label = { Text(text = label) },
            trailingIcon = {
                Icon(trailingIcon, "ArrowIcon",
                    Modifier.clickable { expanded = !expanded })
            },
            readOnly = true
        )
        // Compute the textField width
        val width = with(LocalDensity.current) {
            textFieldSize.width.toDp()
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(width)
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    onSelectionChange(option)
                    expanded = false
                }) {
                    Text(text = option.toString())
                }
            }
        }
    }
}