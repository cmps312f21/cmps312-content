package cmps312.coroutines.console

import kotlinx.coroutines.*
import cmps312.coroutines.viewmodel.StockQuoteViewModel
import cmps312.coroutines.webapi.SimulatedStockQuoteService
import java.lang.Exception

suspend fun main() {
    /*  By default, if one child failed the whole job is cancelled
        and all incomplete sibling jobs are cancelled.
        Unless supervisorScope is used (see example 12)
     */
    val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        println("Exception thrown somewhere within parent or child: $exception.")
    }

    val startTime = System.currentTimeMillis()
    val job = CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
        val stockQuoteService = SimulatedStockQuoteService()
        val deferred1 = async() { stockQuoteService.getStockQuote("Tesla") }
        try {
            val quote1 = deferred1.await()
            println(">> $quote1")
        } catch (e: Exception) {
            println("Request failed : $e.")
        }

        val deferred2 = async() { stockQuoteService.getStockQuote("Aple") }
        try {
            val quote2 = deferred2.await()
            println(">> $quote2")
        } catch (e: Exception) {
            println("Request failed : $e.")
        }

        val deferred3 = async() { stockQuoteService.getStockQuote("Google") }
        try {
            val quote3 = deferred3.await()
            println(">> $quote3")
        } catch (e: Exception) {
            println("Request failed : $e.")
        }
    }

    job.invokeOnCompletion {
        if (job.isCancelled) {
            println(">>> Job cancelled. ${it?.message}<<<")
        }
        else {
            val executionDuration = System.currentTimeMillis() - startTime
            println(">>> Job done. Total elapse time ${executionDuration / 1000}s <<<")
        }
    }
    // Wait for the job to finish otherwise main will exit
    job.join()
}
