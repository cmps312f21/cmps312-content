package cmps312.yalapay.view.cheque

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.yalapay.viewmodel.ChequeDepositViewModel

@Composable
fun SetReturnReason(onSetSelected: () -> Unit) {
    val chequeDepositViewModel =
        viewModel<ChequeDepositViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val selectedChequeDeposit = chequeDepositViewModel.selectedChequeDeposit

    val cheques = if (selectedChequeDeposit != null)
        chequeDepositViewModel.getCheques(selectedChequeDeposit.chequeNos)
    else
        emptyList()

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            cheques.forEach { cheque ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(cheque.chequeNo.toString() + "     ")
                    Text(text = cheque.status)
                }
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    var expanded by remember { mutableStateOf(false) }
                    var selectedReturnReason by remember{ mutableStateOf("Return Reason:")}
                    Text(
                        "Choose Return Reason:",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Text(selectedReturnReason)
                    Box{
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "Choose Return Reason"
                            )
                        }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            chequeDepositViewModel.returnReasons.forEach { option ->
                                DropdownMenuItem(onClick = {
                                    selectedReturnReason = option
                                    cheque.returnReason = selectedReturnReason
                                    expanded = false
                                }) {
                                    Text(option)
                                }
                            }
                        }
                    }
                }
            }
            Button(onClick = {
                onSetSelected()
            }) {
                Text(text = "Set")
            }
        }

    }
}