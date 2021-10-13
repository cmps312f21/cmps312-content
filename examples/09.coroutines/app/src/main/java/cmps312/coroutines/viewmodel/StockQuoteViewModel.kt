package cmps312.coroutines.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmps312.coroutines.model.StockQuote
import cmps312.coroutines.webapi.SimulatedStockQuoteService
import kotlinx.coroutines.launch

private const val TAG = "StockQuoteViewModel"

enum class JobState {
    RUNNING,
    SUCCESS,
    CANCELLED,
    ERROR
}

class StockQuoteViewModel : ViewModel() {
    private val stockQuoteService = SimulatedStockQuoteService()

    var companyList = mutableStateListOf<String>()
    var selectedCompany = mutableStateOf("")

    private var _jobStatusGetStockQuote = mutableStateOf(JobState.SUCCESS)
    val jobStatusGetStockQuote : State<JobState> = _jobStatusGetStockQuote

    private var _stockQuote = mutableStateOf(StockQuote())
    val stockQuote : State<StockQuote> = _stockQuote

    fun getStockQuote() {
        _jobStatusGetStockQuote.value = JobState.RUNNING
        viewModelScope.launch {
            _stockQuote.value = stockQuoteService.getStockQuote(selectedCompany.value)
            _jobStatusGetStockQuote.value = JobState.SUCCESS
        }
    }

    suspend fun getCompanies() {
        companyList.clear()
        companyList.addAll(
            stockQuoteService.getCompanies()
        )
    }
}