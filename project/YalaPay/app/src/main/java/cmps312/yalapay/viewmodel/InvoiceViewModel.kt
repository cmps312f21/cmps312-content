package cmps312.yalapay.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.AndroidViewModel
import cmps312.yalapay.entity.Invoice
import cmps312.yalapay.repository.CustomerRepository
import cmps312.yalapay.repository.InvoiceRepository

class InvoiceViewModel (appContext: Application) : AndroidViewModel(appContext) {
    private val invoiceRepository = InvoiceRepository(appContext)
    private val customerRepository = CustomerRepository(appContext)

    val invoices = mutableStateListOf<Invoice>()
    var selectedInvoice: Invoice? = null
    val customers = mutableStateMapOf<String, String>()

    init {
        getInvoices()
        getCusomers()
    }

    private fun getCusomers() {
        customers.putAll(customerRepository.getCustomersAsMap())
    }

    fun getInvoices() {
        invoices.clear()
        invoices.addAll(invoiceRepository.getInvoices())
    }

    fun getInvoices(searchText: String) {
        invoices.clear()
        invoices.addAll(invoiceRepository.getInvoices(searchText))
    }

    fun addInvoice(invoice: Invoice) {
        invoiceRepository.addInvoice(invoice)
        invoices += invoice
    }

    fun deleteInvoice(invoice: Invoice) {
        invoiceRepository.deleteInvoice(invoice)
        invoices -= invoice
    }

    fun updateInvoice(invoice: Invoice) {
        invoiceRepository.updateInvoice(invoice)
        val index = invoices.indexOfFirst { invoice.invoiceNo == it.invoiceNo }
        if (index >= 0)
            invoices[index] = invoice
    }
}