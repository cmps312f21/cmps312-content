package cmps312.coroutines.console

import cmps312.coroutines.webapi.getStockQuote
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import cmps312.coroutines.viewmodel.MainViewModel

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val job = GlobalScope.launch {
        val company = "Apple"
        val date = "2021-10-08"

        val viewModel = MainViewModel()
        val symbol = viewModel.getStockSymbol(company)

        // ToDo: call the GetQuote service
        val quote = getStockQuote(symbol, date)
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