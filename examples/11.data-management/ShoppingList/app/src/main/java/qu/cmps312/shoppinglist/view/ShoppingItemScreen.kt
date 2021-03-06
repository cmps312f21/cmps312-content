package qu.cmps312.shoppinglist.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import qu.cmps312.shoppinglist.entity.ShoppingItem
import qu.cmps312.shoppinglist.view.components.Datepicker
import qu.cmps312.shoppinglist.view.components.Dropdown
import qu.cmps312.shoppinglist.view.components.TopBar
import qu.cmps312.shoppinglist.viewmodel.ShoppingViewModel

enum class FormMode { ADD, EDIT }

@Composable
//fun ShoppingItemScreen(shoppingItemId: Long? = null, onNavigateBack: () -> Unit) {
fun ShoppingItemScreen(onNavigateBack: () -> Unit) {
    var formMode = FormMode.ADD
    var screenTitle = "Add Shopping Item"
    var confirmButtonLabel = "Add"

    val viewModel = viewModel<ShoppingViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val shoppingItemsCount = viewModel.shoppingItemsCount.observeAsState()

    var categoryId by remember { mutableStateOf(viewModel.selectedShoppingItem?.categoryId ?: 0) }
    var productId by remember { mutableStateOf(viewModel.selectedShoppingItem?.productId ?: 0) }
    var quantity by remember { mutableStateOf( viewModel.selectedShoppingItem?.quantity ?:0) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val toDay = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    var updatedDate by remember { mutableStateOf( viewModel.selectedShoppingItem?.updatedDate ?: toDay) }

    // In case of Edit Mode get the Shopping Item to edit
    if (viewModel.selectedShoppingItem != null) {
        formMode = FormMode.EDIT
        screenTitle = "Edit Shopping Item"
        confirmButtonLabel = "Update"
    }

    val categories = viewModel.categoriesMap.observeAsState().value
    // Every time categories change ->
    //      Convert a list to a map needed to fill the categories dropdown
    /*val categoryOptions by remember {
        derivedStateOf {
            categories.value?.associate {
                Pair(it.id, it.name)
            }
        }
    }*/

    // Every time categoryId change get the products of the selected category
    var products = viewModel.getProductsMap(categoryId).observeAsState().value
    // Every time products change ->
    //      Convert a list to a map needed to fill the products dropdown
    /*val productOptions by remember {
        derivedStateOf {
            products.value?.associate {
                Pair(it.id, "${it.name} ${it.icon}")
            }
        }
    }*/

    Scaffold(
        topBar = { TopBar( title = screenTitle, onNavigateBack) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Dropdown(
                label = "Select a Category",
                options = categories,
                selectedOptionId = categoryId,
                onSelectionChange = {
                    categoryId = it
                })

            Dropdown(
                label = "Select a Product",
                options = products,
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


            Datepicker(context, "Select Updated Date", initialDate = updatedDate,
                onDateSelected = {  updatedDate = it }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = {
                    if (formMode == FormMode.ADD) {
                        val item = ShoppingItem(
                            productId = productId,
                            quantity = quantity,
                            updatedDate = updatedDate
                        )
                        viewModel.addItem(item)
                        // Reset the productId and quantity to enter them again
                        productId = 0L
                        quantity = 0
                    } else {
                        viewModel.selectedShoppingItem?.let {
                            it.productId = productId
                            it.quantity = quantity
                            it.updatedDate = updatedDate
                            viewModel.updateItem(it)
                            onNavigateBack()
                        }
                    }
                }) {
                Text(text = confirmButtonLabel)
            }

            Text(text = "You have ${shoppingItemsCount.value} in your Shopping Card")

            Button(
                onClick = {
                    coroutineScope.launch {
                        val categories = viewModel.getCategoriesAndProductCounts()
                        categories.entries.forEach {
                            println("${it.key.name} -> ${it.value}")
                        }
                    }

                }) {
                Text(text = "Get Categories - Product Count")
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        val categories = viewModel.getCategoryNamesAndProductCounts()
                        categories.entries.forEach {
                            println("${it.key} -> ${it.value}")
                        }
                    }

                }) {
                Text(text = "Get Category names - Product Count")
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        val categories = viewModel.getCategoriesAndProducts()
                        categories.entries.forEach {
                            println("${it.key.name} -> ${it.value}")
                        }
                    }

                }) {
                Text(text = "Get Categories - Products")
            }
        }
    }
}