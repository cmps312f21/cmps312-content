package cmps312.coroutines.webapi

import cmps312.coroutines.model.StockQuote
import kotlinx.coroutines.*

class SimulatedStockQuoteService {
    private val companies = mapOf(
        "Apple" to "AAPL",
        "Amazon" to "AMZN",
        "Alibaba" to "BABA",
        "Salesforce" to "CRM",
        "Facebook" to "FB",
        "Google" to "GOOGL",
        "IBM" to "IBM",
        "Johnson & Johnson" to "JNJ",
        "Microsoft" to "MSFT",
        "Tesla" to "TSLA")

    suspend fun getStockSymbol(name: String): String {
        delay(1500)
        val symbol = companies[name.trim()]
        return symbol!!
    }

    suspend fun getPrice(symbol: String): Int {
        delay(1000)
        val price = (50..500).random()
        return price
    }

    suspend fun getStockQuote(name: String) = withContext(Dispatchers.IO) {
        if (!companies.containsKey(name.trim()))
            throw Exception("Getting stock quote failed. '$name' does not exit.")
        val symbol = getStockSymbol(name)
        val price = getPrice(symbol)
        StockQuote(name.trim(), symbol, price)
    }

    suspend fun getCompanies(): List<String> {
        println("getCompanies started...")
        delay(400)
        return companies.keys.toList()
    }
}


fun main() = runBlocking { // this: CoroutineScope
    launch { doWorld() }
    println("Hello")
}

// this is your first suspending function
suspend fun doWorld() = withContext(Dispatchers.IO){

    val job = launch {
        delay(400L)
        println("World 1!")
    }
//    launch {
//        delay(200L)
//        println("World! 2")
//    }
//    launch {
//        delay(500L)
//        println("World! 3")
//    }

    job.join()
    println("Done")

}