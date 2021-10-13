package cmps312.coroutines.console

import cmps312.coroutines.viewmodel.MainViewModel
import kotlinx.coroutines.*

suspend fun main() {
    val startTime = System.currentTimeMillis()
    val job = CoroutineScope(Dispatchers.IO).launch {
        val viewModel = MainViewModel()
        val deferred = async { viewModel.getStockQuote("Apple") }
        val quote = deferred.await()
        println(">> $quote")

        val deferred2 = async { viewModel.getStockQuote("Tesla") }
        val quote2 = deferred2.await()
        println(">> $quote2")

        val deferred3 = async { viewModel.getStockQuote("Google") }
        val quote3 = deferred3.await()
        println(">> $quote3")
    }

    job.invokeOnCompletion {
        val executionDuration = System.currentTimeMillis() - startTime
        println(">>> Job done. Total elapse time ${executionDuration/1000}s <<<")
    }
    // Wait for the job to finish otherwise the main will exit without the showing the results
    job.join()
}
