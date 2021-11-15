package cmps312.yalapay.view.report

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.yalapay.entity.Invoice
import cmps312.yalapay.entity.getInvoiceStatus
import cmps312.yalapay.view.components.Datepicker
import cmps312.yalapay.view.components.Dropdown
import cmps312.yalapay.view.components.TopBarWithNavigateBack
import cmps312.yalapay.viewmodel.ReportViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt

@Composable
fun InvoiceReport(onNavigateBack: () -> Unit) {
    val reportViewModel =
        viewModel<ReportViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val invoices =  remember { mutableStateListOf<Invoice>() }

    val context = LocalContext.current
    val toDay = Clock.System.todayAt(TimeZone.currentSystemDefault())

    var invoiceStatus by remember { mutableStateOf("") }
    var fromDate by remember { mutableStateOf(toDay) }
    var toDate by remember { mutableStateOf(toDay) }

    Scaffold(
        topBar = {
            TopBarWithNavigateBack (title = "Invoices Report", onNavigateBack)
        }
    ) {
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
                    invoices.clear()
                    invoices.addAll(reportViewModel.getInvoices(invoiceStatus, fromDate, toDate))
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text(text = "Submit")
            }
            // ToDo: Add Lazy Column
        }
    }
}

@Composable
fun ReportFooter(invoicesCount: Int, totalAmount: Double) {
    Text(
        text = "Invoices Count: $invoicesCount - Total Amount: $totalAmount ",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Blue
        )
    )
}