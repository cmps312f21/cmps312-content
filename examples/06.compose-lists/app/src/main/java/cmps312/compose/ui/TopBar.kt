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

    val sortOptions = mapOf(
        "Sort by Surah number" to SortBy.SURAH_NUMBER,
        "Sort by Surah number - descending" to SortBy.SURAH_NUMBER_DESC,
        "Sort by Surah name" to SortBy.SURAH_NAME,
        "Sort by Surah name - descending" to SortBy.SURAH_NAME_DESC,
        "Sort by aya count" to SortBy.AYA_COUNT,
        "Sort by by aya count - descending" to SortBy.AYA_COUNT_DESC
    )

    //Box(Modifier.wrapContentSize(Alignment.TopEnd)) {
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

            sortOptions.forEach { option ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onSortByChange(option.value)
                }) {
                    Text(option.key)
                }
            }
        }
    }
}