package cmps312.yalapay.view.invoice
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmps312.yalapay.entity.Invoice
import kotlinx.datetime.LocalDate

@Composable
fun SortedInvoices(
    invoices: List<Invoice>,
    startDate: LocalDate,
    dueDate: LocalDate
) {
    invoices.forEach { it.invoiceNo }
    var filteredInvoices = search(invoices, startDate, dueDate)

    if (filteredInvoices.isEmpty()) {
        Text("No cheque found.")
    } else {
        val invoicesCount = filteredInvoices.size
        val totalAmount = filteredInvoices.sumOf { it.amount!! }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { ListHeader() }

            items(filteredInvoices) {
              InvoiceCard(
                  invoice = it,
                  onUpdateInvoice = { /*TODO*/ },
                  onDeleteInvoice = { /*TODO*/ },
                  onAddPayment = { /*TODO*/ }) {

              }
            }

            item { ListFooter(invoicesCount, totalAmount) }
        }
    }
}


@Composable
fun search(
    invoices: List<Invoice>,
    startDate: LocalDate,
    dueDate: LocalDate
): List<Invoice> {
   return  invoices.filter {
       it.dueDate!!.year <= dueDate.year && it.dueDate!!.monthNumber <= dueDate.monthNumber && it.dueDate!!.dayOfMonth <= dueDate.dayOfMonth
               && (it.invoiceDate!!.year >= startDate.year && it.invoiceDate!!.monthNumber >= startDate.monthNumber
               && it.invoiceDate!!.dayOfMonth >=startDate.dayOfMonth )
   }

}

//  val selectedDate by remember { mutableStateOf( "${startDate.dayOfMonth}/${startDate.monthNumber}/${startDate.year}" ) }


@Composable
fun ListFooter(invoicesCount: Int, totalAmount: Double) {
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

@Composable
fun ListHeader() {
    Text(
        text = "Invoices Report",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.Blue
        )
    )
}