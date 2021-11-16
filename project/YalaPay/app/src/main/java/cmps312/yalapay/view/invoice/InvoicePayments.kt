package cmps312.yalapay.view.invoice

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.yalapay.entity.Payment
import cmps312.yalapay.entity.balance
import cmps312.yalapay.entity.status
import cmps312.yalapay.ui.theme.LightGreen
import cmps312.yalapay.ui.theme.LightYellow
import cmps312.yalapay.ui.theme.Orange
import cmps312.yalapay.view.components.ImageDialog
import cmps312.yalapay.view.components.TopBarWithNavigateBack
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
            TopBarWithNavigateBack (title = "Invoice Payments", onNavigateBack)
        }
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .border(width = 2.dp, color = Orange, shape = RoundedCornerShape(8.dp))
            ,
            elevation = 16.dp,
            backgroundColor = LightYellow
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                invoiceViewModel.selectedInvoice?.apply {
                    Text(text = "Invoice # $invoiceNo ($status)")
                    Text(text = "Amount: $amount - Balance: $balance")
                    Text(text = "Customer #$customerId - $customerName")
                    Column {
                        if (paymentViewModel.payments.isEmpty()) {
                            Text(text = "No payments Found", modifier = Modifier.fillMaxWidth())
                        }
                        LazyColumn(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
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
    var openImageDialog by remember { mutableStateOf(false) }
    Card(
        elevation = 16.dp,
        backgroundColor = LightGreen,
        modifier = Modifier.padding(8.dp)
                           .border(width = 2.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
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
                payment.cheque?.let { cheque ->
                    Text(text = cheque.toString())

                    if (cheque.chequeImageUri.isNotEmpty()) {
                        val chequeImageId = LocalContext.current.resources.getIdentifier(
                            cheque.chequeImageUri, "drawable",
                            LocalContext.current.packageName
                        )
                        Image(
                            painter = painterResource(id = chequeImageId),
                            contentDescription = "Cheque Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(120.dp)
                                .clickable { openImageDialog = true }
                        )
                        ImageDialog(openImageDialog, chequeImageId,
                            onOpenDialogChange = { openImageDialog = it })
                    }
                }
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

