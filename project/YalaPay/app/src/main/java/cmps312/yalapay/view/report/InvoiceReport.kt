package cmps312.yalapay.view.report

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.yalapay.entity.getChequeDepositStatus
import cmps312.yalapay.entity.getInvoiceStatus
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import cmps312.yalapay.view.components.Datepicker
import cmps312.yalapay.view.components.Dropdown
import cmps312.yalapay.view.invoice.SortedInvoices
import cmps312.yalapay.viewmodel.InvoiceViewModel
import kotlinx.datetime.todayAt

@Composable
fun InvoiceReport(onNavigateBack: () -> Unit) {
    val invoiceViewModel =
        viewModel<InvoiceViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val invoices = invoiceViewModel.invoices
    val context = LocalContext.current
    val toDay = Clock.System.todayAt(TimeZone.currentSystemDefault())

    var invoiceStatus by remember { mutableStateOf("") }
    var fromDate by remember { mutableStateOf(toDay) }
    var toDate by remember { mutableStateOf(toDay) }

    Scaffold {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {

            Datepicker("From Date", initialDate = fromDate,
                onDateChange = { fromDate = it }
            )
            Datepicker("To Date", initialDate = toDate,
                onDateChange = { toDate = it }
            )

            Dropdown(label = "Invoice Status",
                options = getInvoiceStatus(),
                selectedOption = invoiceStatus,
                onSelectionChange = { invoiceStatus = it }
            )

            Button(
                onClick = {
                    //ToDo - Complete the report
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text(text = "Submit")
            }
            SortedInvoices(invoices = invoices, startDate = fromDate, dueDate = toDate)
        }
    }
}