package cmps312.yalapay.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import cmps312.yalapay.repository.InvoiceRepository
import cmps312.yalapay.repository.PaymentRepository

class ReportViewModel (appContext: Application) : AndroidViewModel(appContext) {
    private val invoiceRepository = InvoiceRepository(appContext)
    private val paymentRepository = PaymentRepository(appContext)

    fun getInvoicesSummary() = invoiceRepository.getInvoicesSummary()
    fun getChequesSummary() = paymentRepository.getChequesSummary()
}