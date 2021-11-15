package cmps312.yalapay.repository

import android.content.Context
import cmps312.yalapay.entity.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class XOldAppRepository(private val context: Context) {

    private fun readData(filename: String) =
        context.assets.open(filename).bufferedReader().use { it.readText() }

    fun getAccounts() =
        Json.decodeFromString<List<BankAccount>>(readData("bank-accounts.json"))

    fun getBanks() =
        Json.decodeFromString<List<String>>(readData("banks.json"))

   fun getInvoices() =
        Json { ignoreUnknownKeys = true }.decodeFromString<List<Invoice>>(readData("invoices.json"))

    fun getCustomers() =
        Json.decodeFromString<List<Customer>>(readData("customers.json"))

    fun getCheques()=
        Json.decodeFromString<List<Cheque>>(readData("cheques.json"))

    fun getCheques(chequeNos: List<Int>) =
        getCheques().filter {  chequeNos.contains( it.chequeNo ) }

    fun getChequeDeposits()=
        Json.decodeFromString<List<ChequeDeposit>>(readData("cheque-deposits.json"))

    fun getReturnReasons()=
        Json{ignoreUnknownKeys = true}.decodeFromString<List<String>>(readData("return-reasons.json"))

}

