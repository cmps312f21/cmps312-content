package cmps312.yalapay.viewmodel

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import cmps312.yalapay.entity.*
import cmps312.yalapay.repository.XOldAppRepository
import kotlinx.datetime.LocalDate
import java.util.*

class OldAppViewModel(appContext: Application) : AndroidViewModel(appContext) {
    //hold all the data of the screens
    val appRepository = XOldAppRepository(appContext)

    ////////////////////////////All Required Lists//////////////////////////////////////////////////
    val accounts = mutableStateListOf(*appRepository.getAccounts().toTypedArray())
    val banks = mutableStateListOf(*appRepository.getBanks().toTypedArray())
    val invoices = mutableStateListOf(*appRepository.getInvoices().toTypedArray())
    //val users = mutableStateListOf(*appRepository.getUsers().toTypedArray())
    val customers = mutableStateListOf(*appRepository.getCustomers().toTypedArray())
    val cheques = mutableStateListOf(*appRepository.getCheques().toTypedArray())
    val chequeDeposits = mutableStateListOf(*appRepository.getChequeDeposits().toTypedArray())
    val returnReasons = mutableStateListOf(*appRepository.getReturnReasons().toTypedArray())
   // val payments// = mutableStateListOf(*appRepository.getPayments(invoiceNO = invoiceNo).toTypedArray())

    //////////////////////////////////Selected Variables////////////////////////////////////////////
    var selectedCustomer: Customer? by mutableStateOf(null)
    var selectedInvoice: Invoice? by mutableStateOf(null)
    var selectedAccount: BankAccount? by mutableStateOf(null)
    var selectedChequeDeposit: ChequeDeposit? by mutableStateOf(null)
    var selectedCheques = mutableSetOf<Cheque>()
    var selectedPayment: Cheque? by mutableStateOf(null)

    ////////////////////////////////Customers///////////////////////////////////////////////////////
    fun searchCustomers(searchText: String): List<Customer> {
        return customers.filter { c ->
            c.customerId.toString().lowercase()
                        .contains(searchText.lowercase())
        }
    }

    fun addCustomer(newCustomer: Customer) {
        customers.add(newCustomer)
    }

    fun updateCustomer(updatedCustomer: Customer) {
        var index = customers.indexOf(customers.find { it.customerId == updatedCustomer.customerId })
        customers[index] = updatedCustomer
    }

    fun deleteCustomer(customerId: Int){
        var cust = customers.find {  it.customerId.equals(customerId) }
        if (cust != null)
            customers.remove(cust)
    }

    ///////////////////////////////////Invoices//////////////////////////////////////////////////////
    fun searchInvoices(searchText: String): List<Invoice> {
        return invoices.filter { i ->
            i.invoiceNo!!.toString().toLowerCase(Locale.getDefault())
                .contains(searchText.lowercase(Locale.getDefault()))
        }
    }

    fun addInvoice(invoiceNo:Int, customerId: Int, amount:Double, startDate: LocalDate, dueDate:LocalDate){
        var newInvoice = Invoice(invoiceNo, customerId, amount, startDate, dueDate)
        invoices.add(newInvoice)
    }

    fun deleteInvoice(){
        //selectedInvoice?.payments!!.forEach { deletePayment(it) }
        invoices.remove(selectedInvoice)
    }

    fun updateInvoice(invoiceNo:Int, customerId: Int, amount:Double, startDate: LocalDate, dueDate:LocalDate){
        var newInvoice = Invoice(invoiceNo, customerId, amount, startDate, dueDate)
        var wanted = invoices.indexOf(invoices.find { it.invoiceNo == invoiceNo })
        invoices[wanted] = newInvoice
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    fun searchPayments(searchText: String, invoiceNo: Int): List<Cheque> {
/*        var payments = invoices.find { it.invoiceNo == invoiceNo  }?.payments
        if (payments != null) {
            return payments.filter { p ->
                p.chequeNo!!.toString().toLowerCase(Locale.getDefault())
                    .contains(searchText.lowercase(Locale.getDefault()))
            }
        }
        else*/
            return emptyList()
    }

    fun deletePayment(payment: Cheque){
        //var paym = selectedInvoice?.payments?.find {it.chequeNo == payment.chequeNo }
        //selectedInvoice?.payments?.remove(paym)
        //cheques.remove(paym)
    }

    fun addPaymentToInvoice(chequeNo: Int, chequeAmount:Double,
                            drawer:String, drawerBank:String, paymentStatus: String,
                            receivedDate: LocalDate, dueDate:LocalDate ){
        var newPayment = Cheque(chequeNo = chequeNo,
            amount = chequeAmount, drawer = drawer, bankName = drawerBank,
            status = paymentStatus, receivedDate = receivedDate, dueDate = dueDate )
        //selectedInvoice?.payments!!.add(newPayment)
        cheques.add(newPayment)
    }

    fun updatePayment(chequeNo:Int, chequeAmount:Double, drawer:String, drawerBank:String, paymentStatus: String, recievedDate: LocalDate, dueDate:LocalDate ){
        var newPayment = Cheque(chequeNo = chequeNo, amount = chequeAmount, drawer = drawer, bankName = drawerBank, status = paymentStatus, receivedDate = recievedDate, dueDate = dueDate )
       // var wanted = selectedInvoice?.payments!!.indexOf(selectedInvoice?.payments!!.find { it.chequeNo == chequeNo })
        //selectedInvoice?.payments!![wanted] = newPayment
    }
    /////////////////////////////////////////////Cheques////////////////////////////////////////////
    fun searchCheques(searchText: String): List<Cheque> {
        return cheques.filter {
            it.drawer!!.toLowerCase(Locale.getDefault())
                .contains(searchText.lowercase(Locale.getDefault()))
        }
    }

    fun getCheques(chequeNos: List<Int>) =
        appRepository.getCheques().filter {  chequeNos.contains( it.chequeNo ) }

    fun filterCheques(filter: String, fcheques: List<Cheque>):List<Cheque>{
        return when (filter) {
            "all" -> fcheques
            else -> fcheques.filter { it.status.equals(filter) }
        }
    }

    fun addChequeDeposit(bankAccount: BankAccount) {
        var chequesToDeposit = mutableListOf<Int>()
        selectedCheques.forEach { chequesToDeposit.add(it.chequeNo) }
        var chequeDeposit = ChequeDeposit(
            bankAccountNo = bankAccount.accountNo,
            chequeNos = chequesToDeposit)
        chequeDeposits.add(chequeDeposit)
    }

    fun deleteChequeDeposit(chequeDeposit: ChequeDeposit){
        //ToDo: fix this
        /*chequeDeposit.chequeNos!!.forEach {
            if(it.status!!.equals("Cashed")){
                it.cashedDate = null
            }else{
                it.returnedDate = null
            }
            it.status = "Awaiting"
        }*/
        chequeDeposits.remove(chequeDeposit)
    }
}
