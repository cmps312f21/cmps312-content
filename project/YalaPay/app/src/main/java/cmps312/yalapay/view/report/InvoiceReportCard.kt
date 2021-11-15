package cmps312.yalapay.view.report


import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cmps312.yalapay.entity.Invoice
import cmps312.yalapay.entity.balance
import cmps312.yalapay.entity.status

@Composable
fun InvoiceReportCard(invoice: Invoice) {
    Card(
        elevation = 10.dp,
        backgroundColor = Color.LightGray,
        modifier = Modifier
            .padding(10.dp)

    ) {
        Row(
            modifier = Modifier
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Inbox,
                contentDescription = "Invoice Icon",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Column(modifier = Modifier.weight(3f)) {
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
}