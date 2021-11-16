package cmps312.yalapay.view.customer

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.yalapay.entity.Customer
import cmps312.yalapay.ui.theme.LightYellow
import cmps312.yalapay.view.components.SearchBox
import cmps312.yalapay.view.components.TopSearchBar
import cmps312.yalapay.viewmodel.CustomerViewModel

@Composable
fun CustomersList(onCustomerEdit: () -> Unit,
                  onAddCustomer: () -> Unit) {
    val customerViewModel =
        viewModel<CustomerViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopSearchBar(
                searchText = searchText,
                onSearchTextChange = {
                    searchText = it
                    customerViewModel.getCustomers(searchText)
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    customerViewModel.selectedCustomer = null
                    onAddCustomer()
                },
                content = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Customer")
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = if (customerViewModel.customers.isEmpty())
                                     Arrangement.Center
                                   else Arrangement.Top
        ){
            if (customerViewModel.customers.isEmpty()) {
                Text(text = "No Customers Found.",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 20.sp)
            }
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(customerViewModel.customers) { customer ->
                    CustomerCard(
                        customer,
                        onUpdateCustomer = {
                            customerViewModel.selectedCustomer = customer
                            onCustomerEdit()
                        },
                        onDeleteCustomer = {
                            customerViewModel.deleteCustomer(customer)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CustomerCard(customer: Customer, onUpdateCustomer: () -> Unit, onDeleteCustomer: () -> Unit) {
    Card(
        elevation = 8.dp,
        backgroundColor = LightYellow,
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f),
            ) {
                Text(text = customer.toString())
                Text(text = customer.address.toString())
                Text(text = customer.contactDetails.toString())
            }
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier
                        .clickable { onUpdateCustomer() }
                        .size(20.dp)
                )
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .clickable { onDeleteCustomer() }
                        .size(20.dp)
                )
            }
        }
    }
}