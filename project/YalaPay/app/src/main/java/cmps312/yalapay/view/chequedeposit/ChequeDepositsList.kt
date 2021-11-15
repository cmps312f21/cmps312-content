package cmps312.yalapay.view.chequedeposit

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.yalapay.entity.ChequeDeposit
import cmps312.yalapay.entity.FormMode
import cmps312.yalapay.viewmodel.ChequeDepositViewModel

@Composable
fun ChequeDepositsList(onAddChequeDeposit: ()-> Unit,
                       onViewChequeDeposit: ()-> Unit,
                       onUpdateChequeDeposit: ()-> Unit
){
    val chequeDepositViewModel =
        viewModel<ChequeDepositViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val chequeDeposits = chequeDepositViewModel.chequeDeposits

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    chequeDepositViewModel.selectedChequeDeposit = null
                    onAddChequeDeposit()
                },
                content = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Invoice")
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement =
            if (chequeDeposits.isEmpty())
                Arrangement.Center else Arrangement.Top
        ) {
            if (chequeDeposits.isEmpty()) {
                Text(
                    "No Cheque Deposit found.", modifier = Modifier.align(
                        Alignment.CenterHorizontally
                    )
                )
            } else
                LazyColumn {
                    items(chequeDeposits) {
                        ChequeDepositCard(chequeDeposit = it,
                            onUpdateChequeDeposit = {
                                chequeDepositViewModel.selectedChequeDeposit = it
                                chequeDepositViewModel.chequeDepositScreenMode = FormMode.UPDATE
                                onUpdateChequeDeposit()
                            },
                            onDeleteChequeDeposit = {
                                chequeDepositViewModel.deleteChequeDeposit(it)
                                chequeDepositViewModel.selectedChequeDeposit = null
                            },
                            onViewChequeDeposit = {
                                chequeDepositViewModel.selectedChequeDeposit = it
                                chequeDepositViewModel.chequeDepositScreenMode = FormMode.VIEW
                                onViewChequeDeposit()
                            }
                        )
                    }
                }
        }
    }
}


@Composable
fun ChequeDepositCard(chequeDeposit: ChequeDeposit,
                      onViewChequeDeposit: ()-> Unit,
                      onUpdateChequeDeposit: ()-> Unit,
                      onDeleteChequeDeposit: ()-> Unit) {
    Card(
        elevation = 20.dp,
        backgroundColor = Color.White,
        modifier = Modifier.padding(8.dp).fillMaxSize()
            .clickable {
                onViewChequeDeposit()
            }
    ) {

        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Row() {
                Column() {
                    Text(text = "Deposit Id: ${chequeDeposit.depositId}")
                    Text(text = "Deposit Date: ${chequeDeposit.depositDate} ")
                    Text(text = "Cheques Count: ${chequeDeposit.chequeNos.count()}")
                }
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier
                            .clickable { onUpdateChequeDeposit() }
                            .size(20.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier
                            .clickable { onDeleteChequeDeposit() }
                            .size(20.dp)
                    )
                }
            }
        }
    }
}