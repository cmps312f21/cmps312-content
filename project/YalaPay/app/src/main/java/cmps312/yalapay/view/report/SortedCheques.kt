package cmps312.yalapay.view.cheque

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
import cmps312.yalapay.entity.Cheque
import kotlinx.datetime.LocalDate
import java.util.*

@Composable
fun SortedCheques(
    cheques: List<Cheque>,
    startDate: LocalDate,
    dueDate: LocalDate,
    status:String
) {
    cheques.forEach { println(it.chequeNo) }
    println(startDate)
    println(dueDate)
    println(status)

    var filteredCheques = search(cheques, startDate, dueDate, status)

    filteredCheques.forEach {
        println("S" + it.chequeNo)
    }

    if (filteredCheques.isEmpty()) {
        Text("No cheque found.")
    } else {
        val chequesCount = filteredCheques.size
        val totalAmount = filteredCheques.sumOf { it.amount!! }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { ListHeader() }

            items(filteredCheques) {
                //chequeCard(it)
            }

            item { ListFooter(chequesCount, totalAmount) }
        }
    }
}


@Composable
fun search(cheques: List<Cheque>, startDate: LocalDate, dueDate: LocalDate, dropDown: String): List<Cheque> {
    return cheques.filter{
        it.status.equals(dropDown.toLowerCase(Locale.getDefault()))}.filter{
        it.dueDate!!.year <= dueDate.year && it.dueDate!!.monthNumber <= dueDate.monthNumber && it.dueDate!!.dayOfMonth <= dueDate.dayOfMonth
               && (it.receivedDate!!.year >= startDate.year && it.receivedDate!!.monthNumber >= startDate.monthNumber
                && it.receivedDate!!.dayOfMonth >=startDate.dayOfMonth )
    }

}

//  val selectedDate by remember { mutableStateOf( "${startDate.dayOfMonth}/${startDate.monthNumber}/${startDate.year}" ) }


@Composable
fun ListFooter(chequesCount: Int, totalAmount: Double) {
    Text(
        text = "Cheques Count: $chequesCount - Total Amount: $totalAmount ",
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
        text = "Cheques Report",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.Blue
        )
    )
}
