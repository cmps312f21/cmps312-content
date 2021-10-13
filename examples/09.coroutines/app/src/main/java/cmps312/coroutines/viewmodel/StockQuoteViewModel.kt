package cmps312.coroutines.viewmodel

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmps312.coroutines.model.StockQuote
import cmps312.coroutines.view.displayMessage
import cmps312.coroutines.webapi.SimulatedStockQuoteService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        val msg = exception.message ?: "Request failed"
        //displayMessage(LocalContext.current, msg)
        println("Debug: $msg")
    }

    fun getStockQuotesInParallel(companies: List<String>) = flow {
        val deferredStockQuotes = mutableListOf<Deferred<StockQuote>>()

        // Launch get stock quotes in parallel
        viewModelScope.launch(exceptionHandler) {
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