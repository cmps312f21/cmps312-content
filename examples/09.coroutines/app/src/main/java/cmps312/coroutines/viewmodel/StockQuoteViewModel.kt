package cmps312.coroutines.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmps312.coroutines.model.StockQuote
import cmps312.coroutines.webapi.SimulatedStockQuoteService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow

private const val TAG = "ViewModel.Coroutines"
class StockQuoteViewModel : ViewModel() {
    private val stockQuoteService = SimulatedStockQuoteService()

    suspend fun getStockQuote(name: String) =
         stockQuoteService.getStockQuote(name)

    suspend fun getCompanies(): List<String> =
        stockQuoteService.getCompanies()

    fun getStockQuotes(companies: List<String>) = flow {
        companies.forEach {
            val quote = stockQuoteService.getStockQuote(it)
            emit(quote)
        }
    }

    fun getStockQuotesInParallel(companies: List<String>) = flow {
        val deferredStockQuotes = mutableListOf<Deferred<StockQuote>>()

        // Launch get stock quotes in parallel
        viewModelScope.launch {
            companies.forEach {
                deferredStockQuotes.add(async { stockQuoteService.getStockQuote(it) })
            }
        }

        // Await for and emit the result
        deferredStockQuotes.forEach {
            val quote = it.await()
            emit(quote)
        }
    }
}