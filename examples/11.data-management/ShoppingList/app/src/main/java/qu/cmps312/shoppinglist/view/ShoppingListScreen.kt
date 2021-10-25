package qu.cmps312.shoppinglist.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import qu.cmps312.shoppinglist.view.components.DialogBox
import qu.cmps312.shoppinglist.view.components.DialogResult
import qu.cmps312.shoppinglist.view.components.Dropdown
import qu.cmps312.shoppinglist.view.components.TopBar
import qu.cmps312.shoppinglist.entity.ShoppingItem
import qu.cmps312.shoppinglist.viewmodel.ShoppingViewModel

@Composable
fun ShoppingListScreen() {
    val viewModel = viewModel<ShoppingViewModel>()
    val shoppingList = viewModel.shoppingList.observeAsState()
    var isDialogOpen by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopBar("Shopping List") },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { isDialogOpen = true },
                content = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Item")
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            shoppingList.value?.let {
                items(it) { shoppingItem ->
                    ShoppingListItem(shoppingItem, viewModel)
                }
            }
        }
        ShoppingItemDialog(isDialogOpen, onDialogOpenChange = { isDialogOpen = it}, viewModel)
    }
}