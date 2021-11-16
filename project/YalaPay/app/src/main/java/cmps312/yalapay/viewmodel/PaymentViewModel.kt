package cmps312.yalapay.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import cmps312.yalapay.entity.Payment
import cmps312.yalapay.repository.PaymentRepository

class PaymentViewModel (appContext: Application) : AndroidViewModel(appContext) {
    private val paymentRepository = PaymentRepository(appContext)
    val banks = mutableStateListOf<String>()

    // Payments for a particular invoice
    val payments = mutableStateListOf<Payment>()
    var selectedPayment: Payment? = null

    init {
        getBanks()
    }

    private fun getBanks() {
        banks.addAll(paymentRepository.getBanks())
    }

    fun getPayments(invoiceNo: Int) {
        payments.clear()
        payments.addAll(paymentRepository.getPayments(invoiceNo))
    }

    fun addPayment(payment: Payment) {
        paymentRepository.addPayment(payment)
        payments += payment
    }

    fun deletePayment(payment: Payment) {
        println(">> Debug delete payment id ${payment.paymentId}")
        paymentRepository.deletePayment(payment)
        payments -= payment
    }

    fun updatePayment(payment: Payment) {
        paymentRepository.updatePayment(payment)
        val index = payments.indexOfFirst { payment.paymentId == it.paymentId }
        if (index >= 0)
            payments[index] = payment
    }
}