package cmps312.yalapay.view.cheque

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.yalapay.entity.Cheque
import cmps312.yalapay.viewmodel.ChequeDepositViewModel
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.util.*


@Composable
fun chequeCard(cheque: Cheque) {
    var date1 = cheque.dueDate
    var date2 = Clock.System.todayAt(TimeZone.currentSystemDefault())

    var remainingTime = date1!! - date2!!

    var years = remainingTime.years * 365
    var months = remainingTime.months * 30.4166667
    var days = remainingTime.days

    var remainingDays = (years + months + days).toInt()

    var daysColor = Color.Black

    var appear = false

    var imageId: Int = 0

    if (remainingDays != null) {
        if (remainingDays > 0) {
            daysColor = Color(android.graphics.Color.parseColor("#00b300"))
        } else if (remainingDays < 0) {
            daysColor = Color.Red
        }
    }

    if (cheque.chequeImageUri != null) {
        appear = true
        imageId = LocalContext.current.resources.getIdentifier(
            cheque.chequeImageUri,
            "drawable",
            LocalContext.current.packageName
        )
    } else {
        appear = false
    }

    var statusColor = when (cheque.status.lowercase()) {
        "Awaiting" -> "FFF29684"
        "Deposited" -> "FFF7FBA4"
        "Returned" -> "FF98AFEE"
        "Cashed" -> "FF8AF1EA"
        else -> "FFFFFFFF"
    }


    Card(
        elevation = 10.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(25.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Spacer(modifier = Modifier.width(20.dp))
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "ChequeNo",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(text = "${cheque.chequeNo}")
                    Text(
                        text = "Drawer", style = TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(text = "${cheque.drawer}")
                    Text(
                        text = "Status", style = TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "${cheque.status}", style = TextStyle(
                            background = Color(android.graphics.Color.parseColor("#" + statusColor))
                        )
                    )
                    Text(
                        text = "Due Date", style = TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(text = "${cheque.dueDate}")
                    Text(
                        text = "Remaing Days", style = TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(text = remainingDays.toString(), color = daysColor)
                }
                Spacer(modifier = Modifier.width(60.dp))
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Amount", style = TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(text = "${cheque.amount}")
                    Text(
                        text = "Drawer Bank", style = TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(text = "${cheque.bankName}")
                    Text(
                        text = "Received Date", style = TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(text = "${cheque.receivedDate}")
                    if (cheque.status.lowercase().equals("returned")) {
                        Text(
                            text = "Return Reason", style = TextStyle(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(text = "${cheque.returnReason}")
                        Text(
                            text = "Returned Date", style = TextStyle(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(text = "${cheque.returnedDate}")
                    } else if (cheque.status?.lowercase().equals("cashed")) {
                        Text(
                            text = "Cashed Date", style = TextStyle(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(text = "${cheque.cashedDate}")
                    }
                }
            }
            Text(
                text = "Cheque Image", style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
            if (appear) {
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = "Cheque Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(240.dp, 200.dp)
                )
            } else {
                Text("No Image")
            }
        }
    }
}

@Composable
fun ChequeList(onChequesSelected: () -> Unit) {
    val chequeDepositViewModel =
        viewModel<ChequeDepositViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    var selectedItems by rememberSaveable {
        mutableStateOf(setOf<Cheque>())
    }

    var enabled: Boolean

    var cheques = chequeDepositViewModel.getCheques()

    if (cheques.isEmpty()) {
        Text(text = "No Cheques Found")
    }
    var expanded by remember { mutableStateOf(false) }

    var selectedOption by remember { mutableStateOf("All") }

    var filterOptions = listOf("Awaiting", "Deposited", "Cashed", "Returned", "All")

    cheques = when (selectedOption) {
        "all" -> cheques
        else -> cheques.filter { it.status == selectedOption }
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "Filter By: ")
                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Filter by:"
                        )
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        filterOptions.forEach { option ->
                            DropdownMenuItem(onClick = {
                                selectedOption = option
                                println("this here is the option that is selected   " + selectedOption)
                                expanded = false
                            }) {
                                Text(option)
                            }
                        }
                    }
                }
            }
        }


        enabled = selectedItems.isNotEmpty()

        Button(
            onClick = {
                selectedItems.forEach { println(it.chequeNo) }
                selectedItems.forEach { it.status = "Deposited" }
                chequeDepositViewModel.selectedCheques = selectedItems.toMutableSet()
                onChequesSelected()
            },
            Modifier.padding(10.dp),
            enabled = enabled
        ) {
            Text(text = "Deposit")
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(cheques) { cheque ->
                var enabled: Boolean
                if (cheque.status == "Awaiting") {
                    enabled = true
                } else {
                    enabled = false
                }
                val checked = selectedItems.contains(cheque)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.selectable(
                        selected = checked,
                        onClick = {
                            selectedItems = if (checked) {
                                selectedItems - cheque
                            } else {
                                selectedItems + cheque
                            }
                        },
                        enabled = enabled
                    )
                ) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = {
                            selectedItems = if (checked) {
                                selectedItems - cheque
                            } else {
                                selectedItems + cheque
                            }
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colors.primary,
                            uncheckedColor = Color.DarkGray,
                            checkmarkColor = Color.White,
                            disabledColor = Color.LightGray
                        ),
                        enabled = enabled
                    )
                    chequeCard(cheque = cheque)
                }
            }
        }
    }
}