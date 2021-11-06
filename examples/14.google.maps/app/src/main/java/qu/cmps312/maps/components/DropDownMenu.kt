package qu.cmps312.maps.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import qu.cmps312.maps.entity.MenuOption

@Composable
fun DropDownMenu(onMenuItemClick: (MenuOption) -> Unit,
                 modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    //Box(Modifier.wrapContentSize(Alignment.TopEnd)) {
    Box(modifier = modifier) {
        Button(onClick = {
            expanded = true
        }) {
            Text(text = "More...")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {

            MenuOption.values().forEach { option ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onMenuItemClick(option)
                }) {
                    Text(option.label)
                }
            }
        }
    }
}