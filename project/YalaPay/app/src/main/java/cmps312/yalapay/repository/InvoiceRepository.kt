package cmps312.yalapay.repository

import android.content.Context
import cmps312.yalapay.entity.Invoice
import cmps312.yalapay.entity.InvoicesSummary
import cmps312.yalapay.entity.status
import kotlinx.datetime.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class InvoiceRepository (private val context: Context) {
    private val paymentRepository = PaymentRepository(context)

    companion object {
        var invoices = mutableListOf<Invoice>()
    }

    fun getInvoices() : List<Invoice> {
        if (invoices.isEmpty())
            invoices = Json.decodeFromString<List<Invoice>>(readData(context, "invoices.json")) as MutableList<Invoice>

        // Not very efficient but could not find a better way to get the latest total payments
        for(invoice in invoices)
            invoice.totalPayments = paymentRepository.getTotalPayments(invoice.invoiceNo)

        return invoices
    }

    fun getInvoices(searchText: String) =
        if (searchText.isEmpty())
            getInvoices()
        else
            getInvoices().filter {
                it.invoiceNo.toString().contains(searchText) ||
                        it.customerId.toString().contains(searchText) ||
                        it.amount.toString().contains(searchText)
            }

    fun addInvoice(invoice: Invoice) {
        invoices += invoice
    }

    fun deleteInvoice(invoice: Invoice) {
        invoices -= invoice
    }

    fun updateInvoice(invoice: Invoice) {
        val index = invoices.indexOfFirst {  invoice.invoiceNo == it.invoiceNo}
        if (index >= 0)
            invoices[index] = invoice
    }

    fun getInvoicesSummary() : InvoicesSummary {
        val today = Clock.System.todayAt(TimeZone.currentSystemDefault())
        val invoices = getInvoices()
        val invoicesTotal = invoices.sumOf { it.amount }
        val invoicesInLess30Days = invoices
            .filter { it.dueDate.minus(today).days <= 30 }
            .sumOf { it.amount }

        val invoicesInMore30Days = invoices
            .filter { it.dueDate.minus(today).days > 30 }
            .sumOf { it.amount }

        return InvoicesSummary(invoicesTotal, invoicesInLess30Days, invoicesInMore30Days)
    }

    fun getInvoices(invoiceStatus: String,
                    fromDate: LocalDate, toDate: LocalDate): List<Invoice> {
        val invoices = getInvoices()
        return invoices.filter { (it.invoiceDate in fromDate..toDate) &&
                (it.status == invoiceStatus || invoiceStatus == "All") }
    }
}