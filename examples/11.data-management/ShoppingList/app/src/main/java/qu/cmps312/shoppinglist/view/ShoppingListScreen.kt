package qu.cmps312.shoppinglist.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import qu.cmps312.shoppinglist.viewmodel.ShoppingViewModel

@Composable
fun ShoppingListScreen(onAddItem: () -> Unit, onEditItem: () -> Unit) {
     /* Get an instance of the shared viewModel
        Make the activity the store owner of the viewModel
        to ensure that the same viewModel instance is used for all destinations
    */
    val viewModel = viewModel<ShoppingViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val shoppingList = viewModel.shoppingList.observeAsState()
    val shoppingItemsCount = viewModel.shoppingItemsCount.observeAsState()

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = {
                    Text(
                        text = "Shopping List (${shoppingItemsCount.value})",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.selectedShoppingItem = null
                    onAddItem()
                },
                content = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Item")
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            shoppingList.value?.let { shoppingItems ->
                items(shoppingItems) { shoppingItem ->
                    ShoppingListItem(
                        shoppingItem,
                        onEditItem = {
                            viewModel.selectedShoppingItem = it
                            onEditItem()
                        },
                        onEditQuantity = {
                            viewModel.updateQuantity(it)
                        },
                        onDeleteItem = {
                            viewModel.deleteItem(it)
                            // Show a snack bar and allow the user to undo delete
                            scope.launch {
                                val snackBarResult = scaffoldState.snackbarHostState
                                    .showSnackbar(
                                        message = "${it.productName} deleted",
                                        actionLabel = "Undo",
                                    )
                                if (snackBarResult == SnackbarResult.ActionPerformed) {
                                    // Undo delete shopping item
                                    viewModel.addItem(it)
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}