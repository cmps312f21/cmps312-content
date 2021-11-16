package qu.cmps312.shoppinglist.view.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import qu.cmps312.shoppinglist.entity.ShoppingItem
import qu.cmps312.shoppinglist.view.components.DropdownForMap
import qu.cmps312.shoppinglist.view.components.TopBar
import qu.cmps312.shoppinglist.viewmodel.ShoppingViewModel

enum class FormMode { ADD, EDIT }

@Composable
//fun ShoppingItemScreen(shoppingItemId: Long? = null, onNavigateBack: () -> Unit) {
fun ShoppingItemScreen(onNavigateBack: () -> Unit) {
    var formMode = FormMode.ADD
    var screenTitle = "Add Shopping Item"
    var confirmButtonLabel = "Add"

    val shoppingViewModel = viewModel<ShoppingViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    //val shoppingItemsCount = viewModel.shoppingItemsCount.observeAsState()

    var categoryId by remember { mutableStateOf(shoppingViewModel.selectedShoppingItem?.categoryId ?: "") }
    var productId by remember { mutableStateOf(shoppingViewModel.selectedShoppingItem?.productId ?: "") }
    var quantity by remember { mutableStateOf( shoppingViewModel.selectedShoppingItem?.quantity ?:0) }
    var productName = shoppingViewModel.selectedShoppingItem?.productName ?: ""

    // In case of Edit Mode get the Shopping Item to edit
    if (shoppingViewModel.selectedShoppingItem != null) {
        formMode = FormMode.EDIT
        screenTitle = "Edit Shopping Item"
        confirmButtonLabel = "Update"
    }

    val categories = shoppingViewModel.categories.observeAsState()
    // Every time categories change ->
    //      Convert a list to a map needed to fill the categories dropdown
    val categoryOptions by remember {
        derivedStateOf {
            categories.value?.associate {
                Pair(it.id, it.name)
            }
        }
    }

    // Every time categoryId change get the products of the selected category
    var products = shoppingViewModel.getProducts(categoryId).observeAsState()
    // Every time products change ->
    //      Convert a list to a map needed to fill the products dropdown
    val productOptions by remember {
        derivedStateOf {
            products.value?.associate {
                if (it != null) {
                    Pair(it.id, "${it.name} ${it.icon}")
                }
                else Pair ("", "")
            }
        }
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar( title = screenTitle, onNavigateBack) }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            DropdownForMap(
                label = "Select a Category",
                options = categoryOptions,
                selectedOptionId = categoryId,
                onSelectionChange = { selectedCategoryId, _ ->
                        categoryId = selectedCategoryId
                })

            DropdownForMap(
                label = "Select a Product",
                options = productOptions,
                selectedOptionId = productId,
                onSelectionChange = { selectedProductId, selectedProductName ->
                    productId = selectedProductId
                    productName = selectedProductName
                })

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
                            productName = productName,
                            quantity = quantity,
                            categoryId = categoryId
                        )
                        shoppingViewModel.addItem(item)
                        // Reset the productId and quantity to enter them again
                        productId = ""
                        quantity = 0
                    } else {
                        shoppingViewModel.selectedShoppingItem?.let {
                            it.productId = productId
                            it.productName = productName
                            it.quantity = quantity
                            it.categoryId = categoryId
                            shoppingViewModel.updateItem(it)
                            onNavigateBack()
                        }
                    }

                    scope.launch {
                        val confirmationMsg = "$productName " + (if (formMode == FormMode.ADD) "added" else "updated")
                        scaffoldState.snackbarHostState.showSnackbar( message = confirmationMsg )
                    }
                }) {
                Text(text = confirmButtonLabel)
            }

            //Text(text = "You have ${shoppingItemsCount.value} in your Shopping Card")
        }
    }
}