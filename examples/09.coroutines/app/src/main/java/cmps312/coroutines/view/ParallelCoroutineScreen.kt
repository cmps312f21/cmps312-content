package cmps312.coroutines.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.coroutines.view.components.ClickCounter
import cmps312.coroutines.view.components.Dropdown
import cmps312.coroutines.viewmodel.MainViewModel
import cmps312.coroutines.viewmodel.StockQuote
import kotlinx.coroutines.launch

enum class UiState {
    LOADING,
    SUCCESS
    //ERROR
}

@Composable
fun ParallelCoroutineScreen() {
    val coroutineScope = rememberCoroutineScope()
    val viewModel = viewModel<MainViewModel>()

    var selectedCompany by remember {  mutableStateOf("") }
    var getStockQuoteState by remember {  mutableStateOf(UiState.SUCCESS) }
    var stockQuote by remember { mutableStateOf(StockQuote()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Sequential vs. Parallel Coroutines",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }) {
        Column(
            Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Dropdown(label = "Company",
                options = viewModel.companies.keys.toList(),
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

            Box(contentAlignment = Alignment.BottomStart) {
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