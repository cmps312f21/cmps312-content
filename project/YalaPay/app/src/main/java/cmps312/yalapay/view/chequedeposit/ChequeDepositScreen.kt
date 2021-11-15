package cmps312.yalapay.view.chequedeposit

import androidx.activity.ComponentActivity
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
import cmps312.yalapay.view.components.*
import cmps312.yalapay.viewmodel.ChequeDepositViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt


@Composable
fun ChequeDepositScreen(onNavigateBack: ()->Unit) {
    val depositViewModel =
        viewModel<ChequeDepositViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val selectedDeposit = depositViewModel.selectedChequeDeposit

    val isAddMode = (depositViewModel.chequeDepositScreenMode == FormMode.ADD)
    val isViewMode = (depositViewModel.chequeDepositScreenMode == FormMode.VIEW)
    val isUpdateMode = (depositViewModel.chequeDepositScreenMode == FormMode.UPDATE)
    val isAddOrUpdateMode = (depositViewModel.chequeDepositScreenMode != FormMode.VIEW)

    val screenTitle = if (selectedDeposit != null)
            "Cheques Deposit (#${selectedDeposit.depositId})"
        else
            "Add Cheques Deposit"

    var includedCheques = remember { mutableStateListOf<Int>() }
    val returnedCheques = remember { mutableStateMapOf<Int, String>() }

    if (selectedDeposit != null) {
        includedCheques.clear()
        includedCheques.addAll(selectedDeposit.chequeNos)
    }

    val cheques = if (isAddMode)
        depositViewModel.getCheques(ChequeStatus.AWAITING)
    else
        selectedDeposit?.chequeNos?.let {
            depositViewModel.getCheques(it)
        } ?: emptyList()

    if (cheques.isEmpty())
        displayMessage(message = "No more cheques available.")

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
            depositViewModel.updateChequeDeposit(chequeDeposit, returnedCheques)
        }
        onNavigateBack()
    }

    Scaffold(
        topBar = {
            if (isViewMode || cheques.isEmpty())
                TopBarWithNavigateBack(title = screenTitle,
                    onNavigateBack = onNavigateBack)
           else
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
                    val isReturned = cheque.chequeNo in returnedCheques.keys
                    val isReturnedSwitchVisible =
                        (isUpdateMode && depositStatus == ChequeDepositStatus.CASHED_WITH_RETURNS.label)
                    ChequeCard(
                        cheque,
                        isIncluded = isIncluded,
                        isIncludeSwitchEnabled = isAddMode,
                        onChequeIncludedChange = { included ->
                            includedCheques.remove(cheque.chequeNo)
                            if (included)
                                includedCheques += cheque.chequeNo
                        },

                        returnReasons = depositViewModel.returnReasons,
                        isReturned = isReturned,
                        onChequeReturnedChange = { returned, returnReason ->
                            returnedCheques.remove(cheque.chequeNo)
                            if (returned)
                                returnedCheques += Pair(cheque.chequeNo, returnReason)
                        },
                        isReturnedSwitchVisible = isReturnedSwitchVisible
                    )
                }
            }
        }
    }
}