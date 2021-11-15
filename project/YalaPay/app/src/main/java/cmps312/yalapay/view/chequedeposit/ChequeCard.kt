package cmps312.yalapay.view.chequedeposit

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cmps312.yalapay.entity.Cheque
import cmps312.yalapay.view.components.Dropdown
import kotlinx.datetime.*


@Composable
fun ChequeCard(
    cheque: Cheque,
    isIncluded: Boolean,
    onChequeIncludedChange: (Boolean) -> Unit,
    isIncludeSwitchEnabled: Boolean = true,
    returnReasons: List<String>,
    isReturned: Boolean,
    onChequeReturnedChange: (Boolean, String) -> Unit,
    isReturnedSwitchVisible: Boolean = true
) {
    var isIncluded by remember {  mutableStateOf(isIncluded) }
    var isReturned by remember {  mutableStateOf(isReturned) }
    var returnReason by remember {  mutableStateOf(returnReasons[0]) }

    Card(
        elevation = 16.dp,
        backgroundColor = if (!isIncluded) Color.LightGray else Color.Yellow,
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .border(
                width = 2.dp, color = Color.DarkGray,
                shape = RoundedCornerShape(16.dp)
            ),
            /*.combinedClickable(
                onClick = { },
                onLongClick = {
                    //enabled = true
                    included = !included
                    onChequeIncludeChange(included)
                }
            )*/
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp).width(250.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Cheque No: ${cheque.chequeNo}")
            Text(text = "Amount: ${cheque.amount}")
            Text(text = "Status: ${cheque.status}")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Due Date  : ${cheque.dueDate}")
                DaysToDueDateText(dueDate = cheque.dueDate)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Include: ",
                    modifier = Modifier.padding(10.dp).align(Alignment.CenterVertically)
                )
                Switch(
                    checked = isIncluded,
                    onCheckedChange = {
                        isIncluded = it
                        onChequeIncludedChange(it)
                    },
                    enabled = isIncludeSwitchEnabled,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            if (isReturnedSwitchVisible) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Is returned: ",
                        modifier = Modifier.padding(10.dp).align(Alignment.CenterVertically)
                    )
                    Switch(
                        checked = isReturned,
                        onCheckedChange = {
                            isReturned = it
                            onChequeReturnedChange(it, returnReason)
                        },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

                if (isReturned) {
                    Dropdown(label = "Return Reason",
                        options = returnReasons,
                        selectedOption = returnReason,
                        onSelectionChange = {
                            returnReason = it
                            onChequeReturnedChange(isReturned, returnReason)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DaysToDueDateText(dueDate: LocalDate) {
    val daysToDueDate = getDaysToDueDate(dueDate)
    Text(
        text =
            if (daysToDueDate > 0)
                "(+$daysToDueDate)"
            else "(+$daysToDueDate)",

        color =
            if (daysToDueDate > 0)
                Color.Green
            else Color.Red
    )
}

fun getDaysToDueDate(dueDate: LocalDate) =
    Clock.System.todayAt(TimeZone.currentSystemDefault()).daysUntil(dueDate)