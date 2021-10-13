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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.coroutines.view.components.ClickCounter
import cmps312.coroutines.view.components.Dropdown
import cmps312.coroutines.view.components.TopBar
import cmps312.coroutines.view.displayMessage
import cmps312.coroutines.viewmodel.JobState
import cmps312.coroutines.viewmodel.StockQuoteViewModel
import kotlinx.coroutines.launch

@Composable
fun StockQuoteScreen() {
    val viewModel = viewModel<StockQuoteViewModel>()

    // LaunchedEffect will be executed when the composable is first launched
    // If ParallelCoroutineScreen recomposes, the companyList will not re-initialize again
    LaunchedEffect(true) {
        viewModel.getCompanies()
    }

    Scaffold(
        topBar = { TopBar("Stock Quote") }
    ) {
        Column(
            Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Dropdown(label = "Company",
                options = viewModel.companyList,
                selectedOption = viewModel.selectedCompany.value,
                onSelectionChange = {
                    viewModel.selectedCompany.value = it
                    viewModel.getStockQuote()
                }
            )

            when (viewModel.jobStatusGetStockQuote.value) {
                JobState.RUNNING -> {
                    CircularProgressIndicator()
                }
                JobState.SUCCESS -> {
                    Text(text = "${viewModel.stockQuote.value}")
                }
            }
        }
    }
}