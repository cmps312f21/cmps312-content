package cmps312.yalapay.view.invoice


import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.yalapay.entity.FormMode
import cmps312.yalapay.entity.Invoice
import cmps312.yalapay.view.components.Datepicker
import cmps312.yalapay.view.components.TopBar
import cmps312.yalapay.viewmodel.InvoiceViewModel
import kotlinx.datetime.*

@Composable
fun InvoiceScreen(onNavigateBack: () -> Unit) {
    val invoiceViewModel =
        viewModel<InvoiceViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val context = LocalContext.current
    val selectedInvoice = invoiceViewModel.selectedInvoice

    var formMode = FormMode.ADD
    var screenTitle = "Add Invoice"

    if (selectedInvoice != null) {
        formMode = FormMode.EDIT
        screenTitle = "Edit Invoice (#${selectedInvoice.invoiceNo})"
    }

    var customerId by remember {
        mutableStateOf(selectedInvoice?.customerId?.toString() ?: "")
    }
    var amount by remember {
        mutableStateOf(selectedInvoice?.amount?.toString() ?: "")
    }
    val today = Clock.System.todayAt(TimeZone.currentSystemDefault())
    var invoiceDate by remember {
        mutableStateOf(selectedInvoice?.invoiceDate ?: today)
    }
    var dueDate by remember {
        mutableStateOf(selectedInvoice?.dueDate ?: today.plus(15, DateTimeUnit.DAY))
    }

    fun onSubmit() {
        val invoice = Invoice(
            customerId = customerId.toInt(),
            amount = amount.toDouble(),
            invoiceDate = invoiceDate,
            dueDate = dueDate
        )

        if (formMode == FormMode.ADD) {
            invoiceViewModel.addInvoice(invoice)
        } else {
            invoice.invoiceNo = selectedInvoice?.invoiceNo!!
            invoiceViewModel.updateInvoice(invoice)
        }
        onNavigateBack()
    }

    Scaffold(
        topBar = {
            TopBar(
                title = screenTitle,
                onNavigateBack = onNavigateBack,
                onSubmit = { onSubmit() })
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            OutlinedTextField(
                value = customerId,
                onValueChange = { customerId = it },
                label = { Text(text = "Customer Id") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text(text = "Amount") },
                modifier = Modifier.fillMaxWidth()
            )

            Datepicker("Invoice Date", initialDate = invoiceDate,
                onDateChange = { invoiceDate = it }
            )
            Datepicker("Due Date", initialDate = dueDate,
                onDateChange = { dueDate = it }
            )
        }
    }
}
