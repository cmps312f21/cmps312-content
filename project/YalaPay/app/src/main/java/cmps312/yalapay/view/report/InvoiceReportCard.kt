package cmps312.yalapay.view.report


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cmps312.yalapay.entity.Invoice
import cmps312.yalapay.entity.balance
import cmps312.yalapay.entity.status

@Composable
fun InvoiceReportCard(invoice: Invoice) {
    Card(
        elevation = 16.dp,
        backgroundColor = Color.LightGray,
        modifier = Modifier.padding(10.dp)

    ) {
        Column() {
            Text(text = "Invoice # ${invoice.invoiceNo}")
            Text(text = "Customer Id: ${invoice.customerId}")
            Text(text = "Invoice Date: ${invoice.invoiceDate}")
            Text(text = "Due Date: ${invoice.dueDate}")
            Text(text = "Amount: ${invoice.amount}")
            Text(text = "Balance: ${invoice.balance}")
            Text(text = "Status: ${invoice.status}")
        }
    }
}