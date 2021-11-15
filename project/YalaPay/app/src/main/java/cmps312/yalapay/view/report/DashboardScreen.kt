package cmps312.yalapay.view.report

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.yalapay.R
import cmps312.yalapay.viewmodel.ReportViewModel

@Composable
fun DashboardScreen(onInvoiceReport: ()->Unit, onChequeReport: ()->Unit) {
    val reportViewModel = viewModel<ReportViewModel>()
    val invoicesSummary = reportViewModel.getInvoicesSummary()
    val chequesSummary = reportViewModel.getChequesSummary()

    val orange = Color(0xFFE6B110)

    Column() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.dashboard),
                contentDescription = "Dashboard Image",
                Modifier
                    .size(100.dp)
                    .fillMaxSize()
                    .padding(10.dp)
            )
        }
        Card(
            elevation = 10.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
            ) {
                Row {
                    Text(
                        text = "Invoices",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color.Blue
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                }
                Row() {
                    Text(
                        text = "Total:",
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = invoicesSummary.invoicesTotal.toString(),
                        fontSize = 20.sp,
                        color = orange
                    )
                }
                Row() {
                    Text(
                        text = "Due within 30 days:",
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = invoicesSummary.invoicesInLess30Days.toString(),
                        fontSize = 20.sp,
                        color = orange
                    )
                }
                Row() {
                    Text(
                        text = "Due in more than 30 days:",
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = invoicesSummary.invoicesInMore30Days.toString(),
                        fontSize = 20.sp,
                        color = orange
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "Invoice Report...",
            style = TextStyle(color = Color.Blue, textDecoration = TextDecoration.Underline),
            modifier = Modifier.align(CenterHorizontally)
                               .clickable {
                                   onInvoiceReport()
                               }
        )

        Card(
            elevation = 10.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
            ) {
                Row {
                    Text(
                        text = "Cheques",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color.Blue
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                }
                Row() {
                    Text(
                        text = "Awaiting:",
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                     Text(
                        text = chequesSummary.chequesAwaiting.toString(),
                        fontSize = 20.sp,
                        color = orange
                    )
                }
                Row() {
                    Text(
                        text = "Deposited:",
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = chequesSummary.chequesDeposited.toString(),
                        fontSize = 20.sp,
                        color = orange
                    )

                }
                Row() {
                    Text(
                        text = "Cashed:",
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = chequesSummary.chequesCashed.toString(),
                        fontSize = 20.sp,
                        color = orange
                    )
                }
                Row() {
                    Text(
                        text = "Returned:",
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = chequesSummary.chequesReturned.toString(),
                        fontSize = 20.sp,
                        color =orange)
                }
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "Cheque Report...",
            style = TextStyle(color = Color.Blue, textDecoration = TextDecoration.Underline),
            modifier = Modifier.align(CenterHorizontally).clickable {
                onChequeReport()
            }
        )
    }
}