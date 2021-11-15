package cmps312.yalapay.view.report

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import cmps312.yalapay.entity.Cheque
import cmps312.yalapay.entity.getChequeStatus
import cmps312.yalapay.view.components.Datepicker
import cmps312.yalapay.view.components.Dropdown
import cmps312.yalapay.view.components.TopBarWithNavigateBack
import cmps312.yalapay.viewmodel.ReportViewModel
import kotlinx.datetime.*

@Composable
fun ChequeReport(onNavigateBack: () -> Unit) {
    val reportViewModel =
        viewModel<ReportViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val cheques =  remember { mutableStateListOf<Cheque>() }
    val toDay = Clock.System.todayAt(TimeZone.currentSystemDefault())

    var chequeStatus by remember { mutableStateOf("All") }
    var fromDate by remember { mutableStateOf(toDay.minus(3, DateTimeUnit.MONTH)) }
    var toDate by remember { mutableStateOf(toDay) }

    Scaffold(
        topBar = {
            TopBarWithNavigateBack (title = "Cheque Report", onNavigateBack)
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

            Dropdown(label = "Cheque Status",
                options = getChequeStatus(),
                selectedOption = chequeStatus,
                onSelectionChange = { chequeStatus = it }
            )

            Button(
                onClick = {
                    cheques.clear()
                    cheques.addAll(reportViewModel.getCheques(chequeStatus, fromDate, toDate))
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text(text = "Submit")
            }
            if (cheques.isEmpty()) {
                Text("No cheques Found.")
            } else {
                val chequesCount = cheques.size
                val chequesTotal = cheques.sumOf { it.amount }

                LazyColumn {
                    items(cheques) { cheque ->
                        ChequeReportCard(cheque)
                    }
                    item()
                    {
                        ChequeReportFooter(chequesCount, totalAmount = chequesTotal)
                    }
                }
            }
        }
    }
}

@Composable
fun ChequeReportFooter(chequesCount: Int, totalAmount: Double) {
    Text(
        text = "Cheques Count: $chequesCount - Total Amount: $totalAmount ",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Blue
        )
    )
}