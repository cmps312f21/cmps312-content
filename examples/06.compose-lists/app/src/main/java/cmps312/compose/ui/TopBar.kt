package cmps312.compose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun TopBar(searchText: String, onSearchTextChange: (String)-> Unit,
           surahType: String, onSurahTypeChange: (String) -> Unit,
           onSortByChange: (SortBy) -> Unit
) {
    val surahTypes = listOf(
        "All",
        "Meccan",
        "Medinan"
    )

    TopAppBar(
        title = { Text("") },
        actions = {
            SearchBox(searchText, onSearchTextChange, modifier = Modifier.weight(7F))

            Dropdown(
                modifier = Modifier.weight(2F),
                options = surahTypes,
                selectedOption = surahType,
                onSelectionChange = onSurahTypeChange
            )

            TopBarMenu(onSortByChange, modifier = Modifier.weight(1F))
        }
    )
}

@Composable
fun TopBarMenu(onSortByChange: (SortBy) -> Unit,
               modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        IconButton(onClick = {
            expanded = true
        }) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = "More..."
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {

            SortBy.values().forEach { option ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onSortByChange(option)
                }) {
                    Text(option.label)
                }
            }
        }
    }
}