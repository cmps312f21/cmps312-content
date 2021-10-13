package cmps312.coroutines.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.coroutines.model.StockQuote
import cmps312.coroutines.view.components.ClickCounter
import cmps312.coroutines.view.components.Dropdown
import cmps312.coroutines.view.components.TopBar
import cmps312.coroutines.view.removeTrailingComma
import cmps312.coroutines.viewmodel.StockQuoteViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

enum class UiState {
    LOADING,
    SUCCESS
    //ERROR
}

@Composable
fun ParallelCoroutineScreen() {
    val coroutineScope = rememberCoroutineScope()
    val viewModel = viewModel<StockQuoteViewModel>()

    var companyList = remember { mutableStateListOf<String>() }

    var selectedCompany by remember { mutableStateOf("") }
    var getStockQuoteState by remember { mutableStateOf(UiState.SUCCESS) }
    var stockQuote by remember { mutableStateOf(StockQuote()) }

    var companies by remember { mutableStateOf("Tesla, Apple, Microsoft, IBM,") }
    var companyStockQuotes = remember { mutableStateListOf<StockQuote>() }
    var runInParallel by remember { mutableStateOf(true) }
    var getStockQuotesState by remember { mutableStateOf(UiState.SUCCESS) }
    var executionDuration by remember { mutableStateOf(0L) }

    // LaunchedEffect will be executed when the composable is first launched
    // If ParallelCoroutineScreen recomposes, the companyList will not re-initialize again
    LaunchedEffect(true) {
        companyList.addAll(viewModel.getCompanies())
    }

    fun onGetStockQuotesClick() {
        val startTime = System.currentTimeMillis()
        getStockQuotesState = UiState.LOADING
        executionDuration = 0L
        companyStockQuotes.clear()
        val companiesList = companies.trim().removeTrailingComma().split(",")
        val job = coroutineScope.launch {

            val stockQuotesFlow = if (runInParallel)
                viewModel.getStockQuotesInParallel(companiesList)
            else
                viewModel.getStockQuotes(companiesList)

            stockQuotesFlow.collect {
                println(it)
                companyStockQuotes.add(it)
            }
        }

        job.invokeOnCompletion {
            executionDuration = System.currentTimeMillis() - startTime
            getStockQuotesState = UiState.SUCCESS
            println(">>> Debug: Job done. Total execution time: ${executionDuration / 1000}s")
        }
    }

    Scaffold(
        topBar = { TopBar("Sequential vs. Parallel Coroutines") }
    ) {
        Column(
            Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Dropdown(label = "Company",
                options = companyList,
                selectedOption = selectedCompany,
                onSelectionChange = {
                    selectedCompany = it
                    coroutineScope.launch {
                        getStockQuoteState = UiState.LOADING
                        stockQuote = viewModel.getStockQuote(selectedCompany)
                        getStockQuoteState = UiState.SUCCESS
                    }
                }
            )

            when (getStockQuoteState) {
                UiState.LOADING -> {
                    CircularProgressIndicator()
                }
                UiState.SUCCESS -> {
                    Text(text = "$stockQuote")
                }
            }

            Divider(
                modifier = Modifier
                    .padding(16.dp)
                    .border(BorderStroke(2.dp, Color.Blue))
            )

            OutlinedTextField(
                value = companies,
                onValueChange = { companies = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Companies") }
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = "Execute in Parallel"
                )
                Switch(
                    checked = runInParallel,
                    onCheckedChange = { runInParallel = it }
                )
                Button(
                    modifier = Modifier.weight(1F),
                    onClick = { onGetStockQuotesClick() }
                ) {
                    Text(text = "Get Stock Prices")
                }
            }

            if (getStockQuotesState == UiState.LOADING)
                CircularProgressIndicator()

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(companyStockQuotes) {
                    Text(text = "$it")
                }
            }

            if (executionDuration > 0)
                Text(text = "Total execution time: ${executionDuration / 1000}s")

            Box(
                contentAlignment = Alignment.BottomStart,
                modifier = Modifier.weight(1F)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                ) {
                    ClickCounter(modifier = Modifier.weight(1F))
                    Text(
                        text = "Click to check that the UI is still responsive \uD83D\uDE03",
                        modifier = Modifier.weight(1F)
                    )
                }
            }
        }
    }
}

