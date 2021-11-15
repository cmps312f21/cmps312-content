package cmps312.yalapay.view.invoice

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.yalapay.entity.Payment
import cmps312.yalapay.view.components.TopBar
import cmps312.yalapay.viewmodel.InvoiceViewModel
import cmps312.yalapay.viewmodel.PaymentViewModel

@Composable
fun InvoicePayments(onNavigateBack: () -> Unit, onUpdatePayment: () -> Unit) {
    val invoiceViewModel =
        viewModel<InvoiceViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val paymentViewModel =
        viewModel<PaymentViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    // LaunchedEffect will be executed when the composable is first launched
    // True argument means that if the screen recomposes, the coroutine will not re-executed
    LaunchedEffect(true) {
        invoiceViewModel.selectedInvoice?.let {
            paymentViewModel.getPayments(it.invoiceNo)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Invoice Details",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                })
        }
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            elevation = 16.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                invoiceViewModel.selectedInvoice?.apply {
                    Text(text = "Invoice Number : $invoiceNo", Modifier.padding(10.dp))
                    Text(text = "Amount:$amount QR", Modifier.padding(10.dp))
                    Text(text = "CustomerID: $customerId", Modifier.padding(10.dp))
                    Column() {
                        if (paymentViewModel.payments.isEmpty()) {
                            Text(text = "No payments Found", modifier = Modifier.fillMaxWidth())
                        }
                        LazyColumn(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            items(paymentViewModel.payments) { payment ->
                                PaymentCard(
                                    payment,
                                    onUpdatePayment = {
                                        paymentViewModel.selectedPayment = payment
                                        onUpdatePayment()
                                    },
                                    onDeletePayment = {
                                        paymentViewModel.selectedPayment = null
                                        paymentViewModel.deletePayment(payment)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentCard(payment: Payment, onUpdatePayment: () -> Unit, onDeletePayment: () -> Unit) {
    Card(
        elevation = 8.dp,
        backgroundColor = Color.White,
        modifier = Modifier.padding(8.dp)
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
                modifier = Modifier
                    .weight(1f),
            ) {
                Text(text = "Payment Id: ${payment.paymentId}")
                Text(text = "Payment mode: ${payment.paymentMode}")
                Text(text = "Amount: ${payment.amount}")
                Text(text = "Payment date: ${payment.paymentDate}")
                if (payment.cheque != null)
                    Text(text = payment.cheque.toString())
            }
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier
                        .clickable { onUpdatePayment() }
                        .size(20.dp)
                )
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .clickable { onDeletePayment() }
                        .size(20.dp)
                )
            }
        }
    }
}

