package cmps312.yalapay.repository

import android.content.Context
import cmps312.yalapay.entity.Customer
import cmps312.yalapay.entity.Invoice
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class CustomerRepository (private val context: Context) {
    companion object {
        var customers = mutableListOf<Customer>()
    }

    fun getCustomers() : List<Customer> {
        if (customers.isEmpty())
            customers = Json.decodeFromString<List<Customer>>(readData(context, "customers.json")) as MutableList<Customer>
        return customers
    }

    fun getCustomer(customerId: Int) = getCustomers().filter { it.customerId == customerId }

    fun getCustomers(searchText: String) =
        if (searchText.isEmpty())
            getCustomers()
        else
            getCustomers().filter {
                it.customerId.toString().contains(searchText) ||
                        it.companyName.contains(searchText)
            }

    fun addCustomer(customer: Customer) {
        customers += customer
    }

    fun deleteCustomer(customer: Customer) {
        customers -= customer
    }

    fun updateCustomer(customer: Customer) {
        val index = customers.indexOfFirst {  customer.customerId == it.customerId}
        if (index >= 0)
            customers[index] = customer
    }
}