package cmps312.yalapay.view.invoice

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CreditScore
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.yalapay.entity.Invoice
import cmps312.yalapay.view.components.TopSearchBar
import cmps312.yalapay.viewmodel.InvoiceViewModel

@Composable
fun InvoicesList(
    onUpdateInvoice: () -> Unit,
    onAddInvoice: () -> Unit,
    onGetPayments: () -> Unit,
    onAddPayment: () -> Unit
) {
    val invoiceViewModel =
        viewModel<InvoiceViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopSearchBar(
                searchText = searchText,
                onSearchTextChange = {
                    searchText = it
                    invoiceViewModel.getInvoices(searchText)
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    invoiceViewModel.selectedInvoice = null
                    onAddInvoice()
                },
                content = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Invoice")
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = if (invoiceViewModel.invoices.isEmpty())
                Arrangement.Center
            else Arrangement.Top
        ){
            if (invoiceViewModel.invoices.isEmpty()) {
                Text(text = "No invoices Found.",
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

                items(invoiceViewModel.invoices) { invoice ->
                    InvoiceCard(
                        invoice,
                        onUpdateInvoice = {
                            invoiceViewModel.selectedInvoice = invoice
                            onUpdateInvoice()
                        },
                        onDeleteInvoice = {
                            invoiceViewModel.selectedInvoice = null
                            invoiceViewModel.deleteInvoice(invoice)
                        },
                        onAddPayment = {
                            invoiceViewModel.selectedInvoice = invoice
                            onAddPayment()
                        },
                        onGetPayments = {
                            invoiceViewModel.selectedInvoice = invoice
                            onGetPayments()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun InvoiceCard(
    invoice: Invoice, onUpdateInvoice: () -> Unit, onDeleteInvoice: () -> Unit,
    onAddPayment: () -> Unit, onGetPayments: () -> Unit
) {
    Card(
        elevation = 8.dp,
        backgroundColor = Color.White,
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onGetPayments() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Invoice No. :${invoice.invoiceNo}")
                Text(text = "Invoice date: ${invoice.invoiceDate}")
                Text(text = "Due date: ${invoice.dueDate}")
                Text(text = "Customer ID: ${invoice.customerId}")
                Text(text = "Amount: ${invoice.amount}")
            }
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier
                        .clickable { onUpdateInvoice() }
                        .size(18.dp)
                )
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .clickable { onDeleteInvoice() }
                        .size(18.dp)
                )
                Icon(
                    imageVector = Icons.Outlined.Payments,
                    contentDescription = "Payments",
                    modifier = Modifier
                        .clickable {  onGetPayments() }
                        .size(18.dp)
                )
                Icon(
                    imageVector = Icons.Outlined.CreditScore,
                    contentDescription = "Pay",
                    modifier = Modifier
                        .clickable {
                            onAddPayment()
                        }
                        .size(18.dp)
                )
            }
        }
    }
}

