package cmps312.yalapay.repository

import android.content.Context
import cmps312.yalapay.entity.*
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayAt
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

    fun getCheques(status: ChequeStatus = ChequeStatus.AWAITING) =
        getCheques().filter { it.status == status.label }

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

    fun getCheques(chequeStatus: String,
                  fromDate: LocalDate, toDate: LocalDate
    ): List<Cheque> {
        val cheques = getCheques()
        return cheques.filter { (it.receivedDate in fromDate..toDate) &&
                    (it.status == chequeStatus || chequeStatus == "All") }
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
        // Set the status of the included Cheques to Deposited
        updateChequesStatus(chequeDeposit.chequeNos, ChequeStatus.DEPOSITED)
        chequeDeposits += chequeDeposit
    }

    fun deleteChequeDeposit(chequeDeposit: ChequeDeposit) {
        // Set the status of the included Cheques back to Awaiting
        updateChequesStatus(chequeDeposit.chequeNos, ChequeStatus.AWAITING)
        chequeDeposits -= chequeDeposit
    }

    fun updateChequeDeposit(chequeDeposit: ChequeDeposit,
                            returnedCheques: Map<Int, String>) {

        var chequeStatus = ChequeStatus.DEPOSITED
        if (chequeDeposit.depositStatus in listOf(ChequeDepositStatus.CASHED.label,
                ChequeDepositStatus.CASHED_WITH_RETURNS.label))
            chequeStatus = ChequeStatus.CASHED

        // Set the status of the included Cheques to Deposited or Cashed
        updateChequesStatus(chequeDeposit.chequeNos, chequeStatus, returnedCheques)

        val index = chequeDeposits.indexOfFirst { chequeDeposit.depositId == it.depositId }
        if (index >= 0)
            chequeDeposits[index] = chequeDeposit
    }

    private fun updateChequesStatus(chequeNos: List<Int>,
                                    chequeStatus: ChequeStatus,
                                    returnedCheques: Map<Int, String> = emptyMap()) {
        val today = Clock.System.todayAt(TimeZone.currentSystemDefault())

        for (cheque in cheques) {
            // Only update the cheques included in the Deposit
            if (cheque.chequeNo !in chequeNos) continue

            if (cheque.chequeNo in returnedCheques.keys) {
                cheque.status = ChequeStatus.RETURNED.label
                cheque.returnedDate = today
            } else {
                cheque.status = chequeStatus.label
                if (chequeStatus == ChequeStatus.CASHED)
                    cheque.cashedDate = today
            }

            updateCheque(cheque)
            println(">> Debug ${cheque.toString()}")
        }
    }

    //// Others
    fun getBanks() =
        Json.decodeFromString<List<String>>(readData(context, "banks.json"))

    fun getReturnReasons()=
        Json.decodeFromString<List<String>>(readData(context, "return-reasons.json"))

    fun getBankAccounts(): Map<String, String> {
        val accounts = Json.decodeFromString<List<BankAccount>>(readData(context, "bank-accounts.json"))
        return accounts.associate {
            Pair(it.accountNo, it.toString())
        }
    }
}