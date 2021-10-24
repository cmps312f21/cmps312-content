package qu.cmps312.shoppinglist.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import qa.edu.cmps312.shoppinglist.ui.theme.ShoppingListTheme
import qu.cmps312.shoppinglist.components.DialogBox
import qu.cmps312.shoppinglist.components.TopBar
import qu.cmps312.shoppinglist.entity.ShoppingItem
import qu.cmps312.shoppinglist.viewmodel.ShoppingViewModel

@Composable
fun ShoppingListScreen() {
    val viewModel = viewModel<ShoppingViewModel>()
    val shoppingList = viewModel.shoppingList.observeAsState()

    var isDialogOpen by remember { mutableStateOf(false) }
    var dialogResult by remember { mutableStateOf(false) }

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
                    ListItem(shoppingItem, viewModel)
                }
            }
        }

        var productId by remember { mutableStateOf("") }
        var quantity by remember { mutableStateOf("") }

        DialogBox(isDialogOpen,
            onDialogOpenChange = { isDialogOpen = it },
            title = "Add Shopping Item",
            content = {  AddItem(productId, quantity,
                            onProductIdChange = { productId = it},
                            onQuantityChange = { quantity = it })
                      },
            onDialogResult = {
                dialogResult = it
                isDialogOpen = false
                if (dialogResult) {
                    val item = ShoppingItem(productId = productId.toLong(),
                        quantity = quantity.toInt())
                    viewModel.addItem(item)
                }
            }
        )
    }
}

@Composable
fun AddItem(productId: String, quantity: String,
            onProductIdChange: (String) -> Unit,
            onQuantityChange: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = productId,
            onValueChange = { onProductIdChange(it) },
            label = { Text("Product Id") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = quantity,
            onValueChange = { onQuantityChange(it) },
            label = { Text("Product Id") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun ListItem(shoppingItem: ShoppingItem, viewModel: ShoppingViewModel) {
    var quantity by remember { mutableStateOf(0) }
    quantity = shoppingItem.quantity
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(start = 8.dp, end =8.dp)
    ) {
        shoppingItem.productName?.let {
            Text(text = it,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .weight(1.5F)
            )
        }
        Row(modifier = Modifier.weight(1F)) {
            IconButton(
                onClick = {
                    shoppingItem.quantity++
                    quantity = shoppingItem.quantity
                    viewModel.updateQuantity(shoppingItem)
                }) {
                Icon(
                    Icons.Outlined.KeyboardArrowUp,
                    contentDescription = "Increase",
                )
            }
            Text(
                text = quantity.toString(),
                modifier = Modifier.padding(top = 8.dp)
            )
            IconButton(
                onClick = {
                    shoppingItem.quantity--
                    quantity = shoppingItem.quantity
                    viewModel.updateQuantity(shoppingItem)
                }) {
                Icon(
                    Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "Decrease",
                )
            }
            IconButton(
                onClick = {
                    viewModel.deleteItem(shoppingItem)
                }) {
                Icon(
                    Icons.Outlined.Delete,
                    contentDescription = "Delete",
                )
            }
        }
    }
}