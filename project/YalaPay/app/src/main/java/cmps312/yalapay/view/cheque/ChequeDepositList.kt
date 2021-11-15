package cmps312.yalapay.view.cheque

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.yalapay.entity.Cheque
import cmps312.yalapay.entity.ChequeDeposit
import cmps312.yalapay.entity.ChequeDepositStatus
import cmps312.yalapay.entity.ChequeStatus
import cmps312.yalapay.viewmodel.ChequeDepositViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt

@Composable
fun ChequeDepositCard(
    chequeDeposit: ChequeDeposit,
    onReturnedSelected: () -> Unit,
    onSelectSet: () -> Unit
) {
    val chequeDepositViewModel =
        viewModel<ChequeDepositViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val cheques = chequeDepositViewModel.getCheques(chequeDeposit.chequeNos)
    var appear by remember { mutableStateOf(false) }

    Card(
        elevation = 10.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(25.dp)
    ) {
        Column()
        {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row {
                        Text(text = "Deposit Date: ", style = TextStyle(fontWeight = FontWeight.Bold))
                        Text(text = "${chequeDeposit.depositDate}")
                    }
                    Row() {
                        Text("Account: ", style = TextStyle(fontWeight = FontWeight.Bold))
                        Text(text = "${chequeDeposit.bankAccountNo}")
                    }
                    Row {
                        var expanded by remember { mutableStateOf(false) }
                        var enabled = false
                        var selected by remember { mutableStateOf(chequeDeposit.status) }
                        var statusOptions = listOf("cashed", "returned", "cashed with returns")
                        Text(text = "Status: ", style = TextStyle(fontWeight = FontWeight.Bold))
                        Text(text = "$selected")
                        Box {
                            enabled = when (chequeDeposit.status) {
                                "deposited" -> true
                                else -> false
                            }
                            IconButton(onClick = { expanded = true }, enabled = enabled) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                                    contentDescription = "Filter by:"
                                )
                            }
                            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                statusOptions.forEach { option ->
                                    DropdownMenuItem(onClick = {
                                        selected = option
                                        chequeDeposit.status = option
                                        expanded = false
                                    }) {
                                        Text(option)
                                    }
                                }
                            }
                        }
                    }
                }
                IconButton(onClick = {
                    chequeDepositViewModel.deleteChequeDeposit(chequeDeposit)
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Cheque Deposit"
                    )
                }
            }
            Row (
                modifier = Modifier.padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
                    ){
                Column {
                    Text(text = "Cheques: ", style = TextStyle(fontWeight = FontWeight.Bold))
                    Column {
                        Spacer(modifier = Modifier.width(25.dp))
                        Row {
                            Text(text = "No. of Cheques:    ")
                            Text("${chequeDeposit.chequeNos!!.size}")
                        }
                        Row {
                            Text(text = "Status:    ")
                            Column() {
                                cheques.forEach { ChequesList(it) }
                            }
                        }
                    }
                }

                // ToDo: move to the ViewModel
                // ToDo: Search Status and set the value to Enum
                cheques.forEach { cheque ->
                    if (cheque.status == ChequeStatus.DEPOSITED.label) {
                        when (chequeDeposit.status) {
                            ChequeDepositStatus.CASHED_WITH_RETURNS.label -> appear = true
                            ChequeDepositStatus.CASHED.label -> cheques.forEach { cheque ->
                                cheque.status = ChequeStatus.CASHED.label
                                cheque.cashedDate = Clock.System.todayAt(TimeZone.currentSystemDefault())
                            }
                            ChequeDepositStatus.RETURNED.label -> {
                                cheques.forEach { cheque ->
                                    cheque.status = ChequeStatus.RETURNED.label
                                    cheque.returnedDate = Clock.System.todayAt(TimeZone.currentSystemDefault())
                                }
                                appear = true
                            }
                        }
                    }
                }

                Spacer(modifier= Modifier.width(10.dp))

                var chStatus = chequeDeposit.status
                if (appear) {
                    if(chStatus == "cashed with returns"){
                        Button(onClick = {
                            chequeDepositViewModel.selectedChequeDeposit = chequeDeposit
                            onSelectSet()
                        }) {
                            Text(text = "Set Cheques Status", style = TextStyle(fontSize = 15.sp))
                        }
                    }else if(chStatus == "returned"){
                        Button(onClick = {
                            chequeDepositViewModel.selectedChequeDeposit = chequeDeposit
                            onReturnedSelected()
                        }) {
                            Text(text = "Set Return Reasons", style = TextStyle(fontSize = 15.sp))
                        }
                    }

                }
            }
        }


    }
}

@Composable
fun ChequeDepositList(onReturnedSelected: () -> Unit, onSelectSet: () -> Unit) {
    val chequeDepositViewModel =
        viewModel<ChequeDepositViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val chequeDeposits = chequeDepositViewModel.chequeDeposits
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(chequeDeposits) { chequeD ->
            ChequeDepositCard(chequeD, onReturnedSelected, onSelectSet)
        }
    }
}

@Composable
fun ChequesList(cheque: Cheque) {
    Row() {
        Text("${cheque.chequeNo}     ")
        Text("${cheque.status}")
    }
}