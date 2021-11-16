package cmps312.yalapay.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.AndroidViewModel
import cmps312.yalapay.entity.Customer
import cmps312.yalapay.repository.CustomerRepository

class CustomerViewModel (appContext: Application) : AndroidViewModel(appContext) {
    private val customerRepository = CustomerRepository(appContext)
    val customers = mutableStateListOf<Customer>()

    var selectedCustomer: Customer? = null

    init {
        getCustomers()
    }

    fun getCustomers() {
        customers.clear()
        customers.addAll(customerRepository.getCustomers())
    }

    fun getCustomers(searchText: String) {
        customers.clear()
        customers.addAll(customerRepository.getCustomers(searchText))
    }

    fun addCustomer(customer: Customer) {
        customerRepository.addCustomer(customer)
        customers += customer
    }

    fun deleteCustomer(customer: Customer) {
        customerRepository.deleteCustomer(customer)
        customers -= customer
    }

    fun updateCustomer(customer: Customer) {
        customerRepository.updateCustomer(customer)
        val index = customers.indexOfFirst {  customer.customerId == it.customerId }
        if (index >= 0)
            customers[index] = customer
    }
}