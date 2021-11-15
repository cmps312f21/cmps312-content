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
import kotlinx.datetime.*


@Composable
fun ChequeCard(
    cheque: Cheque,
    isIncluded: Boolean,
    onChequeIncludeChange: (Boolean) -> Unit,
    isEnabled: Boolean = true
) {
    var included by remember {
        mutableStateOf(isIncluded)
    }
    Card(
        elevation = 16.dp,
        backgroundColor = if (!included) Color.LightGray else Color.Yellow,
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
            //.aspectRatio(1.0f)
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
            modifier = Modifier.padding(8.dp).width(220.dp),
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
                    checked = included,
                    onCheckedChange = {
                        included = it
                        onChequeIncludeChange(it)
                    },
                    enabled = isEnabled,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
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