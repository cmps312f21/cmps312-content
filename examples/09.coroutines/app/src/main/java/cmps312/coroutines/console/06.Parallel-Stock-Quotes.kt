package cmps312.coroutines.console

import cmps312.coroutines.viewmodel.StockQuoteViewModel
import cmps312.coroutines.webapi.SimulatedStockQuoteService
import kotlinx.coroutines.*

suspend fun main() {
    val startTime = System.currentTimeMillis()
    val job = CoroutineScope(Dispatchers.IO).launch {
        val stockQuoteService = StockQuoteViewModel()
        val deferred = async { stockQuoteService.getStockQuote("Apple") }
        val deferred2 = async { stockQuoteService.getStockQuote("Tesla") }
        val deferred3 = async { stockQuoteService.getStockQuote("Google") }

        val quote = deferred.await()
        println(">> $quote")

        val quote2 = deferred2.await()
        println(">> $quote2")

        val quote3 = deferred3.await()
        println(">> $quote3")
    }

    job.invokeOnCompletion {
        val executionDuration = System.currentTimeMillis() - startTime
        println(">>> Job done. Total execution time: ${executionDuration/1000}s <<<")
    }
    // Wait for the job to finish otherwise the main will exit without the showing the results
    job.join()
}
