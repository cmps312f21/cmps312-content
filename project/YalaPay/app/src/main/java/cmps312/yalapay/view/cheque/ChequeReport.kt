package cmps312.yalapay.view.cheque

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import cmps312.yalapay.view.components.Datepicker
import cmps312.yalapay.viewmodel.PaymentViewModel
import kotlinx.datetime.todayAt

enum class ChequeReportStatus { All, Awaiting, Deposited, Cashed, Returned, GrandTotal }

@Composable
fun ChequeReport(onNavigateBack: () -> Unit) {
    val paymentViewModel =
        viewModel<PaymentViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val cheques = paymentViewModel.getCheques()

    val context = LocalContext.current
    val toDay = Clock.System.todayAt(TimeZone.currentSystemDefault())

    var expandable by remember {
        mutableStateOf(false)
    }

    var chequeStatus by remember { mutableStateOf("") }
    var fromDate by remember { mutableStateOf(toDay) }
    var toDate by remember { mutableStateOf(toDay) }

    var appear by remember { mutableStateOf(false) }

    //All, Pending, PartiallyPaid, Paid, GrandTotal
    var statusOptions = mapOf(
        "All" to ChequeReportStatus.All,
        "Awaiting" to ChequeReportStatus.Awaiting,
        "Deposited" to ChequeReportStatus.Deposited,
        "Cashed" to ChequeReportStatus.Cashed,
        "Returned" to ChequeReportStatus.Returned,
    )

    Scaffold{
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {

            Datepicker(context, "From Date", initialDate = fromDate,
                onDateSelected = { fromDate = it }
            )
            Datepicker(context, "To Date", initialDate = toDate,
                onDateSelected = { toDate = it }
            )

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { expandable = !expandable }) {
                OutlinedTextField(
                    value = chequeStatus,
                    onValueChange = { },
                    enabled = false,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Status")
                    }
                )
                DropdownMenu(expanded = expandable, onDismissRequest = { expandable = false }) {
                    statusOptions.forEach { status ->
                        DropdownMenuItem(onClick = {
                            chequeStatus = status.key
                            expandable = false
                        }) {
                            Text(text = "${status.key}", fontWeight = FontWeight.Bold)
                        }

                    }
                }

            }

            Button(
                onClick = {
                    if (chequeStatus.equals("")) {
                        Toast.makeText(
                            context, "Please Enter Status!",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        appear = true
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text(text = "Submit")
            }

            if (appear) {
                SortedCheques(cheques = cheques, fromDate, toDate, chequeStatus)
            }
        }
    }
}