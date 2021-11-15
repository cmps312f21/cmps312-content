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
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.yalapay.entity.ChequeDeposit
import cmps312.yalapay.entity.ChequeDepositStatus
import cmps312.yalapay.entity.FormMode
import cmps312.yalapay.viewmodel.ChequeDepositViewModel

@Composable
fun ChequeDepositsList(onAddChequeDeposit: ()-> Unit,
                       onViewChequeDeposit: ()-> Unit,
                       onUpdateChequeDeposit: ()-> Unit
){
    val depositViewModel =
        viewModel<ChequeDepositViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val chequeDeposits = depositViewModel.chequeDeposits

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    depositViewModel.selectedChequeDeposit = null
                    depositViewModel.chequeDepositScreenMode = FormMode.ADD
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
                                depositViewModel.selectedChequeDeposit = it
                                depositViewModel.chequeDepositScreenMode = FormMode.UPDATE
                                onUpdateChequeDeposit()
                            },
                            onDeleteChequeDeposit = {
                                depositViewModel.deleteChequeDeposit(it)
                                depositViewModel.selectedChequeDeposit = null
                            },
                            onViewChequeDeposit = {
                                depositViewModel.selectedChequeDeposit = it
                                depositViewModel.chequeDepositScreenMode = FormMode.VIEW
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
        Row {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(4.dp).weight(1f)
            ) {
                Text(text = "Deposit #${chequeDeposit.depositId} on ${chequeDeposit.depositDate}")
                Text(text = "Status: ${chequeDeposit.depositStatus} ")
                Text(text = "Cheques Count: ${chequeDeposit.chequeNos.count()}")
            }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(
                    imageVector = Icons.Outlined.Visibility,
                    contentDescription = "View",
                    modifier = Modifier
                        .clickable { onViewChequeDeposit() }
                        .size(18.dp)
                )
                // Only deposits with status Deposited can be changed or deleted
                if (chequeDeposit.depositStatus == ChequeDepositStatus.DEPOSITED.label) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier
                            .clickable { onUpdateChequeDeposit() }
                            .size(18.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier
                            .clickable { onDeleteChequeDeposit() }
                            .size(18.dp)
                    )
                }
            }
        }
    }
}