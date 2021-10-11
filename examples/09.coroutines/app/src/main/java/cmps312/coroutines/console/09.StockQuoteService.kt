package cmps312.coroutines.console

import cmps312.coroutines.viewmodel.MainViewModel
import cmps312.coroutines.webapi.StockQuoteService
import kotlinx.coroutines.*

suspend fun main() {
    val startTime = System.currentTimeMillis()
    val job = CoroutineScope(Dispatchers.IO).launch {
        val company = "Apple"
        val date = "2021-10-08"

        val viewModel = MainViewModel()
        val symbol = viewModel.getStockSymbol(company)

        val quote = StockQuoteService.getStockQuote(symbol, date)
        println(">> $company (${quote.symbol}) = ${quote.price}")
        println(">> MarketStockQuote: $quote")
    }

    job.invokeOnCompletion {
        val executionDuration = System.currentTimeMillis() - startTime
        println(">>> Job done. Total elapse time ${executionDuration/1000}s <<<")
    }
    // Wait for the job to finish otherwise main will exit
    job.join()
}