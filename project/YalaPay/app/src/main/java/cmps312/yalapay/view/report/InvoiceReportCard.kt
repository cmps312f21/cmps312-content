package cmps312.yalapay.view.report


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cmps312.yalapay.entity.Invoice
import cmps312.yalapay.entity.balance
import cmps312.yalapay.entity.status
import cmps312.yalapay.ui.theme.LightSilver
import cmps312.yalapay.ui.theme.LightYellow

@Composable
fun InvoiceReportCard(invoice: Invoice) {
    Card(
        elevation = 16.dp,
        backgroundColor = LightYellow,
        modifier = Modifier.padding(8.dp).fillMaxWidth()
            .border(width = 2.dp, color = LightSilver, shape = RoundedCornerShape(8.dp))
    ) {
        Column (modifier = Modifier.padding(16.dp)) {
            Text(text = "Invoice # ${invoice.invoiceNo} (${invoice.status})")
            Text(text = "Invoice date: ${invoice.invoiceDate}")
            Text(text = "Due date: ${invoice.dueDate}")
            Text(text = "Customer #${invoice.customerId} - ${invoice.customerName}")
            Text(text = "Amount: ${invoice.amount} - Balance: ${invoice.balance}")
        }
    }
}