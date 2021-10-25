package qu.cmps312.shoppinglist.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import qu.cmps312.shoppinglist.entity.ShoppingItem
import qu.cmps312.shoppinglist.view.components.Dropdown
import qu.cmps312.shoppinglist.view.components.TopBar
import qu.cmps312.shoppinglist.viewmodel.ShoppingViewModel

enum class FormMode { ADD, EDIT }

@Composable
fun ShoppingItemScreen(shoppingItemId: Long? = null, onNavigateBack: () -> Unit) {
    var formMode = FormMode.ADD
    var screenTitle = "Add Shopping Item"
    var confirmButtonLabel = "Add"

    val viewModel = viewModel<ShoppingViewModel>()
    var shoppingItem by remember {
        mutableStateOf<ShoppingItem?>(null)
    }

    val shoppingItemsCount = viewModel.shoppingItemsCount.observeAsState()

    var categoryId by remember { mutableStateOf(0L) }
    var productId by remember { mutableStateOf(0L) }
    var quantity by remember { mutableStateOf(0) }

    // In case of Edit Mode get the Shopping Item to edit
    if (shoppingItemId != null) {
        LaunchedEffect(key1 = true) {
            shoppingItem = viewModel.getShoppingItem(shoppingItemId)
            shoppingItem?.let {
                productId = it.productId ?: 0
                quantity = it.quantity ?: 0
                categoryId = it.categoryId ?: 0
                viewModel.getProducts(categoryId)
            }
        }

        formMode = FormMode.EDIT
        screenTitle = "Edit Shopping Item"
        confirmButtonLabel = "Update"
    }

    val categories = viewModel.categories.observeAsState()
    // .associate Converts a list to a map needed to fill the categories dropdown
    val emptyMap = mapOf(0L to "")
    val categoryOptions = categories.value?.let {
        it.associate { category -> Pair(category.id, category.name) }
    } ?: emptyMap

    // .associate Converts a list to a map needed to fill the products dropdown
    val productOptions = viewModel.products.associate {
           product -> Pair(product.id, "${product.name} ${product.image}")
    }

    Scaffold(
        topBar = { TopBar( title = screenTitle, onNavigateBack) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Dropdown(
                label = "Select a Category",
                options = categoryOptions,
                selectedOptionId = categoryId,
                onSelectionChange = {
                    categoryId = it
                    viewModel.getProducts(categoryId)
                })

            Dropdown(
                label = "Select a Product",
                options = productOptions,
                selectedOptionId = productId,
                onSelectionChange = { productId = it.toLong() })

            OutlinedTextField(
                value = if (quantity > 0) quantity.toString() else "",
                onValueChange = {
                    quantity = if (it.isNotEmpty()) it.toInt() else 0
                },
                label = { Text("Quantity") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = {
                    if (formMode == FormMode.ADD) {
                        val item = ShoppingItem(
                            productId = productId,
                            quantity = quantity
                        )
                        viewModel.addItem(item)
                        // Reset the productId and quantity to enter them again
                        productId = 0L
                        quantity = 0
                    } else {
                        shoppingItem?.let {
                            it.productId = productId
                            it.quantity = quantity
                            viewModel.updateItem(it)
                            onNavigateBack()
                        }
                    }
                }) {
                Text(text = confirmButtonLabel)
            }

            Text(text = "You have ${shoppingItemsCount.value} in your Shopping Card")
        }
    }
}