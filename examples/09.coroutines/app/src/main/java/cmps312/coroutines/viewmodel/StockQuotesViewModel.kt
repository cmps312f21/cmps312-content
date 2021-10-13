package cmps312.coroutines.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmps312.coroutines.model.StockQuote
import cmps312.coroutines.view.removeTrailingComma
import cmps312.coroutines.webapi.SimulatedStockQuoteService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

private const val TAG = "StockQuotesViewModel"
class StockQuotesViewModel : ViewModel() {
    private val stockQuoteService = SimulatedStockQuoteService()

    var selectedCompanies = mutableStateOf("Tesla, Apple, Microsoft, IBM,")

    private var _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    var companyStockQuotes = mutableStateListOf<StockQuote>()
    var runJobsInParallel = mutableStateOf(true)

    private var _jobStatusGetStockQuotes = mutableStateOf(JobState.SUCCESS)
    val jobStatusGetStockQuotes: State<JobState> = _jobStatusGetStockQuotes

    private var _executionDuration = mutableStateOf(0L)
    val executionDuration: State<Long> = _executionDuration

    private suspend fun getStockQuotes(companies: List<String>) {
        companies.forEach {
            val quote = stockQuoteService.getStockQuote(it)
            companyStockQuotes.add(quote)
        }
    }

    private suspend fun getStockQuotesInParallel(companies: List<String>) =
        withContext(Dispatchers.IO) {
            companies.map { async { stockQuoteService.getStockQuote(it) } }
                     .map { it.await() }
        }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        _errorMessage.value = exception.message ?: "Request failed."
        _jobStatusGetStockQuotes.value = JobState.CANCELLED
        println("Debug: ${_errorMessage.value}")
    }

    fun onGetStockQuotes() {
        val startTime = System.currentTimeMillis()
        _jobStatusGetStockQuotes.value = JobState.RUNNING
        _executionDuration.value = 0L
        companyStockQuotes.clear()

        val companiesList = selectedCompanies.value.trim().removeTrailingComma().split(",")

        val job =
            viewModelScope.launch(exceptionHandler) {
                if (runJobsInParallel.value) {
                    val quotes = getStockQuotesInParallel(companiesList)
                    companyStockQuotes.addAll(quotes)
                } else {
                    getStockQuotes(companiesList)
                }
            }

        job.invokeOnCompletion {
            if (!job.isCancelled) {
                _executionDuration.value = System.currentTimeMillis() - startTime
                _jobStatusGetStockQuotes.value = JobState.SUCCESS
                println(">>> Debug: Job done. Total execution time: ${_executionDuration.value / 1000}s")
            }
        }
    }
}