package cmps312.yalapay.repository

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cmps312.yalapay.entity.Invoice
import cmps312.yalapay.entity.InvoicesSummary
import kotlinx.datetime.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class InvoiceRepository (private val context: Context) {
    companion object {
        var invoices = mutableListOf<Invoice>()
    }

    fun getInvoices() : List<Invoice> {
        if (invoices.isEmpty())
            invoices = Json.decodeFromString<List<Invoice>>(readData(context, "invoices.json")) as MutableList<Invoice>
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
        // ToDo: Implement Invoices Report
        val invoices = getInvoices()
        return invoices
    }

}