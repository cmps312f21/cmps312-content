package cmps312.yalapay.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import cmps312.yalapay.repository.InvoiceRepository
import cmps312.yalapay.repository.PaymentRepository
import kotlinx.datetime.LocalDate

class ReportViewModel (appContext: Application) : AndroidViewModel(appContext) {
    private val invoiceRepository = InvoiceRepository(appContext)
    private val paymentRepository = PaymentRepository(appContext)

    fun getInvoicesSummary() = invoiceRepository.getInvoicesSummary()
    fun getChequesSummary() = paymentRepository.getChequesSummary()

    fun getCheques() = paymentRepository.getCheques()

    fun getInvoices(invoiceStatus: String,
                    fromDate: LocalDate, toDate: LocalDate
    ) = invoiceRepository.getInvoices(invoiceStatus, fromDate, toDate)

    fun getCheques(chequeStatus: String,
                    fromDate: LocalDate, toDate: LocalDate
    ) = paymentRepository.getCheques(chequeStatus, fromDate, toDate)
}