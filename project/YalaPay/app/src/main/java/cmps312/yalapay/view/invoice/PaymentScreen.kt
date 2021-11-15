package cmps312.yalapay.view.invoice

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.yalapay.entity.Cheque
import cmps312.yalapay.entity.FormMode
import cmps312.yalapay.entity.Payment
import cmps312.yalapay.entity.getPaymentModes
import cmps312.yalapay.view.components.Datepicker
import cmps312.yalapay.view.components.Dropdown
import cmps312.yalapay.view.components.TopBarWithSave
import cmps312.yalapay.viewmodel.InvoiceViewModel
import cmps312.yalapay.viewmodel.PaymentViewModel
import kotlinx.datetime.*

@Composable
fun PaymentScreen(onNavigateBack: () -> Unit) {
    val invoiceViewModel =
        viewModel<InvoiceViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val paymentViewModel =
        viewModel<PaymentViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    var selectedPayment = paymentViewModel.selectedPayment
    val invoiceNo = invoiceViewModel.selectedInvoice?.invoiceNo!!
    val context = LocalContext.current

    var formMode = FormMode.ADD
    var screenTitle = "Add Payment"

    if (selectedPayment != null) {
        formMode = FormMode.UPDATE
        screenTitle = "Invoice #$invoiceNo - Edit Payment (#${selectedPayment.paymentId})"
    }

    var amount by remember { mutableStateOf(selectedPayment?.amount?.toString() ?: "") }
    val today = Clock.System.todayAt(TimeZone.currentSystemDefault())
    var paymentDate by remember {
        mutableStateOf(selectedPayment?.paymentDate ?: today)
    }

    var paymentMode by remember {
        mutableStateOf(selectedPayment?.paymentMode ?: "")
    }

    // Cheque
    var chequeNo by remember { mutableStateOf(selectedPayment?.cheque?.chequeNo?.toString() ?: "") }
    var drawer by remember { mutableStateOf(selectedPayment?.cheque?.drawer ?: "") }
    var bankName by remember { mutableStateOf(selectedPayment?.cheque?.bankName ?: "") }
    var dueDate by remember {
        mutableStateOf(selectedPayment?.cheque?.dueDate ?: today.plus(5, DateTimeUnit.DAY))
    }
    var status by remember { mutableStateOf(selectedPayment?.cheque?.status ?: "") }
    var chequeImageUri by remember { mutableStateOf(selectedPayment?.cheque?.chequeImageUri ?: "") }

    fun onSubmit() {
        val cheque =
            if (paymentMode == "Cheque") {
                Cheque(
                    chequeNo = chequeNo.toInt(),
                    amount = amount.toDouble(),
                    drawer = drawer,
                    bankName = bankName,
                    chequeImageUri = chequeImageUri,
                    receivedDate = paymentDate,
                    dueDate = dueDate,
                )
            }
            else null

        val payment = Payment(
            invoiceNo = invoiceNo,
            amount = amount.toDouble(),
            paymentDate = paymentDate,
            paymentMode = paymentMode,
            cheque = cheque,
            chequeNo = cheque?.chequeNo
        )

        if (formMode == FormMode.ADD) {
            paymentViewModel.addPayment(payment)
        } else {
            payment.paymentId = selectedPayment?.paymentId!!
            paymentViewModel.updatePayment(payment)
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
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {

            // To keep it simpler do not allow changing the Payment Mode
            if (formMode == FormMode.UPDATE) {
                Text(text = "Payment Mode: $paymentMode" )
            } else {
                Dropdown(
                    label = "Payment Mode",
                    options = getPaymentModes(),
                    selectedOption = paymentMode,
                    onSelectionChange = { paymentMode = it }
                )
            }

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Datepicker("Payment Date", initialDate = paymentDate,
                onDateChange = {
                    paymentDate = it
                }
            )

            if (paymentMode == "Cheque") {
                // To keep it simpler do not allow changing the chequeNo as it is a primary key
                OutlinedTextField(
                    value = chequeNo,
                    onValueChange = { chequeNo = it },
                    label = { Text("chequeNo") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = formMode == FormMode.UPDATE
                )

                OutlinedTextField(
                    value = drawer,
                    onValueChange = { drawer = it },
                    label = { Text("Drawer") },
                    modifier = Modifier.fillMaxWidth()
                )

                Dropdown(label = "Bank",
                    options = paymentViewModel.banks,
                    selectedOption = bankName,
                    onSelectionChange = { bankName = it }
                )

                Datepicker("Due Date", initialDate = dueDate,
                    onDateChange = {
                        dueDate = it
                    }
                )

                OutlinedTextField(
                    value = chequeImageUri,
                    onValueChange = { chequeImageUri = it },
                    label = { Text("Cheque Image Uri") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}