package cmps312.yalapay.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import cmps312.yalapay.entity.BankAccount
import cmps312.yalapay.entity.Cheque
import cmps312.yalapay.entity.ChequeDeposit
import cmps312.yalapay.repository.PaymentRepository

class ChequeDepositViewModel (appContext: Application) : AndroidViewModel(appContext) {
    private val paymentRepository = PaymentRepository(appContext)

    val chequeDeposits = mutableStateListOf<ChequeDeposit>()
    var selectedChequeDeposit: ChequeDeposit? = null
    var selectedCheques = mutableSetOf<Cheque>()
    var selectedAccount: BankAccount? by mutableStateOf(null)

    val accounts = mutableStateListOf(*paymentRepository.getAccounts().toTypedArray())
    val returnReasons = mutableStateListOf(*paymentRepository.getReturnReasons().toTypedArray())

    fun getCheques() = paymentRepository.getCheques()

    init {
        getChequeDeposits()
    }

    fun getChequeDeposits() {
        chequeDeposits.clear()
        chequeDeposits.addAll(paymentRepository.getChequeDeposits())
    }

    fun addChequeDeposit(chequeDeposit: ChequeDeposit) {
        var chequesToDeposit = mutableListOf<Int>()
        selectedCheques.forEach { chequesToDeposit.add(it.chequeNo) }
        chequeDeposit.chequeNos = chequesToDeposit
        paymentRepository.addChequeDeposit(chequeDeposit)
        chequeDeposits += chequeDeposit
    }

    fun deleteChequeDeposit(chequeDeposit: ChequeDeposit) {
        paymentRepository.deleteChequeDeposit(chequeDeposit)
        chequeDeposits -= chequeDeposit
    }

    fun updateChequeDeposit(chequeDeposit: ChequeDeposit) {
        paymentRepository.updateChequeDeposit(chequeDeposit)
        val index = chequeDeposits.indexOfFirst { chequeDeposit.depositId == it.depositId }
        if (index >= 0)
            chequeDeposits[index] = chequeDeposit
    }

    fun getCheques(chequeNos: List<Int>) = paymentRepository.getCheques(chequeNos)
}