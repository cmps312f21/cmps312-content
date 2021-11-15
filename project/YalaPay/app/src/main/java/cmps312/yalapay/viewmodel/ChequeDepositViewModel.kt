package cmps312.yalapay.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.AndroidViewModel
import cmps312.yalapay.entity.ChequeDeposit
import cmps312.yalapay.entity.ChequeStatus
import cmps312.yalapay.entity.FormMode
import cmps312.yalapay.repository.PaymentRepository

class ChequeDepositViewModel (appContext: Application) : AndroidViewModel(appContext) {
    private val paymentRepository = PaymentRepository(appContext)

    val chequeDeposits = mutableStateListOf<ChequeDeposit>()
    var selectedChequeDeposit: ChequeDeposit? = null
    var chequeDepositScreenMode : FormMode = FormMode.UPDATE

    val bankAccounts = mutableStateMapOf<String, String>()
    val returnReasons = mutableStateListOf<String>()

    fun getCheques() = paymentRepository.getCheques()

    init {
        getChequeDeposits()
        getBankAccounts()
        getRetunReasons()
    }

    private fun getBankAccounts() {
        bankAccounts.putAll(paymentRepository.getBankAccounts())
    }
    private fun getRetunReasons() {
        returnReasons.addAll(paymentRepository.getReturnReasons())
    }

    fun getChequeDeposits() {
        chequeDeposits.clear()
        chequeDeposits.addAll(paymentRepository.getChequeDeposits())
    }

    fun addChequeDeposit(chequeDeposit: ChequeDeposit) {
        paymentRepository.addChequeDeposit(chequeDeposit)
        chequeDeposits += chequeDeposit
    }

    fun deleteChequeDeposit(chequeDeposit: ChequeDeposit) {
        paymentRepository.deleteChequeDeposit(chequeDeposit)
        chequeDeposits -= chequeDeposit
    }

    fun updateChequeDeposit(
        chequeDeposit: ChequeDeposit,
        returnedCheques: Map<Int, String>
    ) {
        paymentRepository.updateChequeDeposit(chequeDeposit, returnedCheques)
        val index = chequeDeposits.indexOfFirst { chequeDeposit.depositId == it.depositId }
        if (index >= 0)
            chequeDeposits[index] = chequeDeposit
    }

    fun getCheques(chequeNos: List<Int>) = paymentRepository.getCheques(chequeNos)
    fun getCheques(status: ChequeStatus = ChequeStatus.AWAITING) = paymentRepository.getCheques(status)
}