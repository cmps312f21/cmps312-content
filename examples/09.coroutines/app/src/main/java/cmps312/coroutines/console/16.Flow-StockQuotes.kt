package cmps312.coroutines.console

import cmps312.coroutines.viewmodel.StockQuoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@InternalCoroutinesApi
suspend fun main() {
    val viewModel = StockQuoteViewModel()
    val companies = listOf("Tesla", "Apple", "Microsoft", "IBM")
    val job = CoroutineScope(Dispatchers.IO).launch {
        viewModel.getStockQuotes(companies).collect { // like observer
            println(it)
        }
    }

    // Wait for the job to finish otherwise main will exit
    job.join()
}