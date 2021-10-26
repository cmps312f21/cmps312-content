package qu.cmps312.shoppinglist.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import qu.cmps312.shoppinglist.entity.ShoppingItem
import qu.cmps312.shoppinglist.viewmodel.ShoppingViewModel

@Composable
fun ShoppingListItem(shoppingItem: ShoppingItem, viewModel: ShoppingViewModel,
                     onEditItem: () -> Unit) {
    var quantity by remember { mutableStateOf(0) }
    quantity = shoppingItem.quantity
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(start = 8.dp, end =8.dp)
    ) {
        shoppingItem.productName?.let {
            Text(text = it,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .weight(1F)
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
                    viewModel.selectedShoppingItem = shoppingItem
                    onEditItem()
                    //onEditItem(shoppingItem.id)
                }) {
                Icon(
                    Icons.Outlined.Edit,
                    contentDescription = "Edit",
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