package cmps312.yalapay.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import cmps312.yalapay.entity.Cheque
import cmps312.yalapay.entity.Payment
import cmps312.yalapay.repository.PaymentRepository

class PaymentViewModel (appContext: Application) : AndroidViewModel(appContext) {
    private val paymentRepository = PaymentRepository(appContext)
    val banks = mutableStateListOf(*paymentRepository.getBanks().toTypedArray())

    val payments = mutableStateListOf<Payment>()
    var selectedPayment: Payment? = null

    init {
        getPayments()
    }

    fun getPayments() {
        payments.clear()
        payments.addAll(paymentRepository.getPayments())
    }

    fun getPayments(searchText: String) {
        payments.clear()
        payments.addAll(paymentRepository.getPayments(searchText))
    }

    fun addPayment(payment: Payment) {
        paymentRepository.addPayment(payment)
        payments += payment
    }

    fun deletePayment(payment: Payment) {
        paymentRepository.deletePayment(payment)
        payments -= payment
    }

    fun updatePayment(payment: Payment) {
        paymentRepository.updatePayment(payment)
        val index = payments.indexOfFirst { payment.paymentId == it.paymentId }
        if (index >= 0)
            payments[index] = payment
    }

    fun getPayments(invoiceNo: Int) = paymentRepository.getPayments(invoiceNo)

    fun getCheques() = paymentRepository.getCheques()
}