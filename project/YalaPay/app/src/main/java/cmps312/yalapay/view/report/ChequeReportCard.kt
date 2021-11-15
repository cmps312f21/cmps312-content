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
import cmps312.yalapay.entity.Cheque

@Composable
fun ChequeReportCard(cheque: Cheque) {
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
                Text(text = "Cheque No: ${cheque.chequeNo}")
                Text(text = "Drawer: ${cheque.drawer}")
                Text(text = "Bank: ${cheque.bankName}")
                Text(text = "Amount: ${cheque.amount}")
                Text(text = "Received Date: ${cheque.receivedDate}")
                Text(text = "Due Date: ${cheque.dueDate}")
                Text(text = "Status: ${cheque.status}")
                if (cheque.cashedDate != null)
                    Text(text = "Cashed Date: ${cheque.cashedDate}")
                if (cheque.returnedDate != null) {
                    Text(text = "Return Date: ${cheque.returnedDate}")
                    Text(text = "Return Reason: ${cheque.returnReason}")
                }
            }
        }
    }
}