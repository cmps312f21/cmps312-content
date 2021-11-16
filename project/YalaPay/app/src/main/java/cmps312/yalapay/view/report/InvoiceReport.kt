package cmps312.yalapay.view.report

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import cmps312.yalapay.entity.getChequeStatus
import cmps312.yalapay.entity.getInvoiceStatus
import cmps312.yalapay.view.components.Datepicker
import cmps312.yalapay.view.components.Dropdown
import cmps312.yalapay.view.components.TopBarWithNavigateBack
import cmps312.yalapay.viewmodel.ReportViewModel
import kotlinx.datetime.*

@Composable
fun InvoiceReport(onNavigateBack: () -> Unit) {
    val reportViewModel =
        viewModel<ReportViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val invoices =  remember { mutableStateListOf<Invoice>() }

    val context = LocalContext.current
    val toDay = Clock.System.todayAt(TimeZone.currentSystemDefault())

    var invoiceStatus by remember { mutableStateOf("All") }
    var fromDate by remember { mutableStateOf(toDay.minus(3, DateTimeUnit.MONTH)) }
    var toDate by remember { mutableStateOf(toDay) }

    Scaffold(
        topBar = {
            TopBarWithNavigateBack (title = "Invoice Report", onNavigateBack)
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Datepicker("From Date", initialDate = fromDate,
                    modifier = Modifier.weight(1f),
                    onDateChange = { fromDate = it }
                )
                Datepicker("To Date", initialDate = toDate,
                    modifier = Modifier.weight(1f),
                    onDateChange = { toDate = it }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Dropdown(label = "Invoice Status",
                    options = getInvoiceStatus(),
                    selectedOption = invoiceStatus,
                    onSelectionChange = { invoiceStatus = it }
                )

                Button(
                    onClick = {
                        invoices.clear()
                        invoices.addAll(reportViewModel.getInvoices(invoiceStatus, fromDate, toDate))
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(text = "Submit")
                }
            }
            if (invoices.isEmpty()) {
                Text("No invoices Found.")
            } else {
                val invoicesCount = invoices.size
                val invoicesTotal = invoices.sumOf { it.amount }

                LazyColumn() {
                    items(invoices) { invoice ->
                        InvoiceReportCard(invoice)
                    }
                    item()
                    {
                        ReportFooter(invoicesCount, invoicesTotal)
                    }
                }
            }
        }
    }
}

@Composable
fun ReportFooter(invoicesCount: Int, invoicesTotal: Double) {
    Text(
        text = "Invoices Count: $invoicesCount - Total: $invoicesTotal ",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Blue
        )
    )
}