package cmps312.yalapay.view.cheque

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.yalapay.entity.BankAccount
import cmps312.yalapay.viewmodel.ChequeDepositViewModel

@Composable
fun AccountsGroup(
    bankAccounts: SnapshotStateList<BankAccount>,
    selectedOptionIndex:Int, onOptionSelected:(Int, BankAccount)->Unit){

    Card(
        backgroundColor = Color.White,
        modifier = Modifier.fillMaxWidth(),
        elevation = 10.dp,
        shape = RoundedCornerShape(25.dp)
    ){
        Column(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
            ){
            bankAccounts.forEachIndexed(){ optionIndex, optionAccount->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (optionIndex == selectedOptionIndex),
                            onClick = { onOptionSelected(optionIndex, optionAccount) }
                        )
                ){
                    RadioButton(
                        selected = (optionIndex == selectedOptionIndex),
                        onClick = {onOptionSelected(optionIndex, optionAccount)}
                    )
                    Text(text = "${optionAccount.toString()}")
                }
            }
        }
    }
}


@Composable
fun SelectAccount(onDepositClick:()->Unit){
    val viewModel =
            viewModel<ChequeDepositViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val accounts = viewModel.accounts

    var selectedAccount by remember{ mutableStateOf(accounts[0])}
    var selectedIndex by remember{ mutableStateOf(0)}

    Scaffold{
        Column (
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            AccountsGroup(bankAccounts = accounts, selectedOptionIndex = selectedIndex,
                onOptionSelected = {
                        index, optionAccount->
                    run{
                        selectedAccount=optionAccount
                        selectedIndex = index
                    }
                }
            )
            Button(onClick = {
                viewModel.selectedAccount = selectedAccount
                //ToDo: make it work
                //viewModel.addChequeDeposit(selectedAccount)
                onDepositClick()
            }
            ) {
                Text("Deposit")
            }
        }
    }
}