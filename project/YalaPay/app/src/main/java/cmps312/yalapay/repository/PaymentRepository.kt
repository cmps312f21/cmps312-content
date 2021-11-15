package cmps312.yalapay.repository

import android.content.Context
import cmps312.yalapay.entity.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class PaymentRepository (private val context: Context) {
    companion object {
        var cheques = mutableListOf<Cheque>()
        var payments = mutableListOf<Payment>()
        var chequeDeposits = mutableListOf<ChequeDeposit>()
    }

    //// Cheques
    fun getCheques(): List<Cheque> {
        if (cheques.isEmpty())
            cheques = Json.decodeFromString<List<Cheque>>(
                readData(context,"cheques.json")
            ) as MutableList<Cheque>
        return cheques
    }

    fun getCheque(chequeNo: Int) = getCheques().find { it.chequeNo == chequeNo }

    fun getCheques(chequeNos: List<Int>) =
        getCheques().filter { chequeNos.contains(it.chequeNo) }

    fun getCheques(searchText: String) =
        if (searchText.isEmpty())
            getCheques()
        else
            getCheques().filter {
                it.chequeNo.toString().contains(searchText) ||
                        it.amount.toString().contains(searchText) ||
                        it.drawer.contains(searchText)
            }

    fun addCheque(cheque: Cheque) {
        cheques += cheque
    }

    fun deleteCheque(cheque: Cheque) {
        cheques -= cheque
    }

    fun updateCheque(cheque: Cheque) {
        val index = cheques.indexOfFirst { cheque.chequeNo == it.chequeNo }
        if (index >= 0)
            cheques[index] = cheque
    }

    fun getChequesSummary() : ChequesSummary {
        val cheques = getCheques()

        val chequesAwaiting = cheques
            .filter { it.status == ChequeStatus.AWAITING.label }
            .sumOf { it.amount }

        val chequesDeposited = cheques
            .filter { it.status == ChequeStatus.DEPOSITED.label }
            .sumOf { it.amount }

        val chequesCashed = cheques
            .filter { it.status == ChequeStatus.CASHED.label }
            .sumOf { it.amount }

        val chequesReturned = cheques
            .filter { it.status == ChequeStatus.RETURNED.label }
            .sumOf { it.amount }

        return ChequesSummary(chequesAwaiting, chequesDeposited, chequesCashed, chequesReturned)
    }

    //// Payments
    fun getPayments(): List<Payment> {
        if (payments.isEmpty())
            payments = Json.decodeFromString<List<Payment>>(
                readData(context,"payments.json")
            ) as MutableList<Payment>
        return payments
    }

    fun getPayments(paymentIds: List<Int>) =
        getPayments().filter { paymentIds.contains(it.paymentId) }

    fun getPayments(searchText: String) =
        if (searchText.isEmpty())
            getPayments()
        else
            getPayments().filter {
                it.paymentId.toString().contains(searchText) ||
                        it.amount.toString().contains(searchText) ||
                        it.invoiceNo.toString().contains(searchText)
            }

    fun getPayments(invoiceNo: Int) : List<Payment> {
        val payments = getPayments().filter { it.invoiceNo == invoiceNo }
        for (payment in payments) {
            if (payment.chequeNo != null) {
                payment.cheque = getCheque(payment.chequeNo!!)
            }
        }
        return payments
    }

    fun getTotalPayments(invoiceNo: Int) = getPayments(invoiceNo).sumOf { it.amount }

    fun addPayment(payment: Payment) {
        payment.cheque?.let {
            addCheque(it)
        }
        payments += payment
    }

    fun deletePayment(payment: Payment) {
        payment.cheque?.let {
            deleteCheque(it)
        }
        payments -= payment
    }

    fun updatePayment(payment: Payment) {
        val index = payments.indexOfFirst { payment.paymentId == it.paymentId }
        if (index >= 0) {
            payments[index] = payment
            payment.cheque?.let {
                updateCheque(it)
            }
        }
    }

    /// ChequeDeposits
    fun getChequeDeposits(): List<ChequeDeposit> {
        if (chequeDeposits.isEmpty())
            chequeDeposits = Json.decodeFromString<List<ChequeDeposit>>(
                readData(context,"cheque-deposits.json")
            ) as MutableList<ChequeDeposit>
        return chequeDeposits
    }

    fun getChequeDeposit(depositId: Int) = getChequeDeposits().filter { it.depositId == depositId }

    fun addChequeDeposit(chequeDeposit: ChequeDeposit) {
        chequeDeposits += chequeDeposit
    }

    fun deleteChequeDeposit(chequeDeposit: ChequeDeposit) {
        chequeDeposits -= chequeDeposit
    }

    fun updateChequeDeposit(chequeDeposit: ChequeDeposit) {
        val index = chequeDeposits.indexOfFirst { chequeDeposit.depositId == it.depositId }
        if (index >= 0)
            chequeDeposits[index] = chequeDeposit
    }
    
    //// Others
    fun getBanks() =
        Json.decodeFromString<List<String>>(readData(context, "banks.json"))

    fun getReturnReasons()=
        Json.decodeFromString<List<String>>(readData(context, "return-reasons.json"))

    fun getAccounts() =
        Json.decodeFromString<List<BankAccount>>(readData(context, "bank-accounts.json"))
}