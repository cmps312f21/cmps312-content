package qu.cmps312.shoppinglist.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import qu.cmps312.shoppinglist.entity.ShoppingItem
import qu.cmps312.shoppinglist.view.components.DialogBox
import qu.cmps312.shoppinglist.view.components.DialogResult
import qu.cmps312.shoppinglist.view.components.Dropdown
import qu.cmps312.shoppinglist.viewmodel.ShoppingViewModel

@Composable
fun ShoppingItemDialog(isDialogOpen: Boolean, onDialogOpenChange: (Boolean)->Unit,
                       viewModel: ShoppingViewModel
) {
    var dialogResult by remember { mutableStateOf(DialogResult.CANCEL) }
    var productId by remember { mutableStateOf(0L) }
    var quantity by remember { mutableStateOf(0) }

    if (isDialogOpen) {
        DialogBox(
            title = "Add Shopping Item", confirmButtonLabel = "Add",
            content = {
                ShoppingItemForm(
                    productId, quantity,
                    onProductIdChange = { productId = it },
                    onQuantityChange = { quantity = it },
                    viewModel
                )
            },
            onDialogResult = {
                dialogResult = it
                if (dialogResult == DialogResult.CONFIRM) {
                    val item = ShoppingItem(
                        productId = productId,
                        quantity = quantity
                    )
                    viewModel.addItem(item)
                } else {
                    onDialogOpenChange(false)
                }
                productId = 0L
                quantity = 0
            }
        )
    }
}

@Composable
fun ShoppingItemForm(productId: Long,
                     quantity: Int,
                     onProductIdChange: (Long) -> Unit,
                     onQuantityChange: (Int) -> Unit,
                     viewModel: ShoppingViewModel) {

    var categoryId by remember { mutableStateOf(0L) }

    val categories = viewModel.categories.observeAsState()
    // .associate Converts a list to a map
    val emptyMap = mapOf(0L to "")
    val categoryOptions = categories.value?.let {
        it.associate { category -> Pair(category.id, category.name) }
    } ?: emptyMap

    // .associate Converts a list to a map
    val productOptions = viewModel.products.associate {
            product -> Pair(product.id, product.name)
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
            onSelectionChange = { onProductIdChange(it) })

        OutlinedTextField(
            value = if (quantity > 0) quantity.toString() else "",
            onValueChange = {
                onQuantityChange(it.toInt())
            },
            label = { Text("Quantity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}