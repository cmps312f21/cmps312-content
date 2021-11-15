package cmps312.yalapay.view.chequedeposit

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.yalapay.entity.*
import cmps312.yalapay.view.components.Datepicker
import cmps312.yalapay.view.components.Dropdown
import cmps312.yalapay.view.components.DropdownForMap
import cmps312.yalapay.view.components.TopBarWithSave
import cmps312.yalapay.viewmodel.ChequeDepositViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt

@ExperimentalFoundationApi
@Composable
fun ChequeDepositScreen(onNavigateBack: ()->Unit) {
    val depositViewModel =
        viewModel<ChequeDepositViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val selectedDeposit = depositViewModel.selectedChequeDeposit

    val isAddMode = (depositViewModel.chequeDepositScreenMode == FormMode.ADD)
    val isViewMode = (depositViewModel.chequeDepositScreenMode == FormMode.VIEW)
    val isEditMode = (depositViewModel.chequeDepositScreenMode != FormMode.VIEW)

    val screenTitle = if (selectedDeposit != null)
            "Edit Cheques Deposit (#${selectedDeposit.depositId})"
        else
            "Add Cheques Deposit"

    var includedCheques = remember { mutableStateListOf<Int>() }
    if (selectedDeposit != null)
        includedCheques.addAll(selectedDeposit.chequeNos)

    val cheques = if (isViewMode)
        selectedDeposit?.chequeNos?.let {
            depositViewModel.getCheques()
        } ?: emptyList()
    else
        depositViewModel.getCheques(ChequeStatus.AWAITING)

    var bankAccountNo by remember {
        mutableStateOf(
            selectedDeposit?.bankAccountNo ?: ""
        )
    }

    var depositStatus by remember {
        mutableStateOf(
            selectedDeposit?.depositStatus ?: ChequeDepositStatus.DEPOSITED.label
        )
    }

    var depositDate by remember {
        mutableStateOf(
            selectedDeposit?.depositDate
                ?: Clock.System.todayAt(TimeZone.currentSystemDefault())
        )
    }

    fun onSubmit() {
        val chequeDeposit = ChequeDeposit(
            bankAccountNo = bankAccountNo,
            depositDate = depositDate,
            depositStatus = depositStatus,
            chequeNos = includedCheques
        )

        if (isAddMode) {
            depositViewModel.addChequeDeposit(chequeDeposit)
        } else {
            chequeDeposit.depositId = selectedDeposit?.depositId!!
            depositViewModel.updateChequeDeposit(chequeDeposit)
        }
        onNavigateBack()
    }

    Scaffold(
        topBar = {
            TopBarWithSave(
                title = screenTitle,
                onNavigateBack = onNavigateBack,
                onSubmit = { onSubmit() })
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            DropdownForMap(label = "Bank Account",
                options = depositViewModel.bankAccounts,
                selectedOptionId = bankAccountNo,
                onSelectionChange = { selectedAccountNo, _ ->
                    bankAccountNo = selectedAccountNo
                })

            Dropdown(label = "Deposit Status",
                options = getChequeDepositStatus(),
                selectedOption = depositStatus,
                onSelectionChange = { depositStatus = it }
            )

            Datepicker("Deposit Date", initialDate = depositDate,
                onDateChange = { depositDate = it }
            )

            LazyRow {
                items(cheques) { cheque ->
                    val isIncluded = cheque.chequeNo in includedCheques
                    ChequeCard(cheque,
                        isIncluded = isIncluded,
                        onChequeIncludeChange = { isIncluded ->
                            if (isIncluded)
                                includedCheques += cheque.chequeNo
                            else
                                includedCheques -= cheque.chequeNo
                        })
                }
            }
        }
    }
}