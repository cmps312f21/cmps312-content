package cmps312.yalapay.view.invoice

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import cmps312.yalapay.view.components.Datepicker
import cmps312.yalapay.viewmodel.InvoiceViewModel
import kotlinx.datetime.todayAt

enum class InvoiceReportStatus { All, Pending, PartiallyPaid, Paid, GrandTotal }

@Composable
fun InvoiceReport(onNavigateBack: () -> Unit) {
    val invoiceViewModel = viewModel<InvoiceViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val invoices = invoiceViewModel.invoices

    val context = LocalContext.current
    val toDay = Clock.System.todayAt(TimeZone.currentSystemDefault())

    var expandable by remember {
        mutableStateOf(false)
    }

    var appear by remember{ mutableStateOf(false)}

    var invoiceStatus by remember { mutableStateOf("") }
    var fromDate by remember { mutableStateOf( toDay) }
    var toDate by remember { mutableStateOf( toDay) }

    //All, Pending, PartiallyPaid, Paid, GrandTotal
    var statusOptions = mapOf(
        "All" to InvoiceReportStatus.All,
        "Pending" to InvoiceReportStatus.Pending,
        "Partially Paid" to InvoiceReportStatus.PartiallyPaid,
        "Paid" to InvoiceReportStatus.Paid,
        "Grand Total" to InvoiceReportStatus.GrandTotal
    )

    Scaffold {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {

                Datepicker("From Date", initialDate = fromDate,
                    onDateChange = {  fromDate = it }
                )
                Datepicker("To Date", initialDate = toDate,
                    onDateChange = {  toDate = it }
                )
            Button(
                onClick = {
                        appear = true
                    },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text(text = "Submit")
            }

            if(appear){
                SortedInvoices(invoices = invoices, startDate = fromDate, dueDate = toDate)
            }
        }
    }
}