package cmps312.yalapay.view.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun TopSearchBar(searchText: String,
                 onSearchTextChange: (String)->Unit) {
    TopAppBar(
        title = {
            Text(text = "")
        },
        actions = {
            SearchBox(searchText, onSearchTextChange)
        }
    )
}