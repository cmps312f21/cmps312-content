package cmps312.coroutines.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmps312.coroutines.model.StockQuote
import cmps312.coroutines.webapi.SimulatedStockQuoteService
import kotlinx.coroutines.launch

enum class JobState {
    RUNNING,
    SUCCESS,
    CANCELLED
}

private const val TAG = "StockQuoteViewModel"
class StockQuoteViewModel : ViewModel() {
    private val stockQuoteService = SimulatedStockQuoteService()

    var companyList = mutableStateListOf<String>()
    var selectedCompany by mutableStateOf("")

    var jobStatusGetStockQuote by mutableStateOf(JobState.SUCCESS)
    var stockQuote by mutableStateOf(StockQuote())

    // Auto initialize the companies list
    init {
        viewModelScope.launch {
            getCompanies()
        }
    }

    fun getStockQuote() {
        this.jobStatusGetStockQuote = JobState.RUNNING
        viewModelScope.launch {
            stockQuote = stockQuoteService.getStockQuote(selectedCompany)
            jobStatusGetStockQuote = JobState.SUCCESS
        }
    }

    suspend fun getCompanies() {
        companyList.clear()
        companyList.addAll(
            stockQuoteService.getCompanies()
        )
    }
}