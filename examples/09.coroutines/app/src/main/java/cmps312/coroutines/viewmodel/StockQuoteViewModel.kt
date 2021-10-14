package cmps312.coroutines.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

class StockQuoteViewModel : ViewModel() {
    private val stockQuoteService = SimulatedStockQuoteService()

    var companyList = mutableStateListOf<String>()
    var selectedCompany by mutableStateOf("")

    var jobStatusGetStockQuote by mutableStateOf(JobState.SUCCESS)
    var stockQuote by mutableStateOf(StockQuote())
    var errorMessage by mutableStateOf("")

    // Auto initialize the companies list
    init {
        viewModelScope.launch {
            getCompanies()
        }
    }

    fun getStockQuote() {
       jobStatusGetStockQuote = JobState.RUNNING
        viewModelScope.launch {
            try {
                stockQuote = stockQuoteService.getStockQuote(selectedCompany)
                jobStatusGetStockQuote = JobState.SUCCESS
            } catch (e: Exception) {
                jobStatusGetStockQuote = JobState.CANCELLED
                errorMessage = e.message ?: "Request failed"
            }
        }
    }

    suspend fun getCompanies() {
        companyList.clear()
        companyList.addAll(
            stockQuoteService.getCompanies()
        )
    }
}