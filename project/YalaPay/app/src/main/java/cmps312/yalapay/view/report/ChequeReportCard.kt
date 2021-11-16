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
import cmps312.yalapay.entity.Cheque
import cmps312.yalapay.ui.theme.LightSilver
import cmps312.yalapay.ui.theme.LightYellow

@Composable
fun ChequeReportCard(cheque: Cheque) {
    Card(
        elevation = 16.dp,
        backgroundColor = LightYellow,
        modifier = Modifier.padding(8.dp).fillMaxWidth()
                            .border(width = 2.dp, color = LightSilver, shape = RoundedCornerShape(8.dp))
    ) {
        Column (modifier = Modifier.padding(16.dp)) {
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