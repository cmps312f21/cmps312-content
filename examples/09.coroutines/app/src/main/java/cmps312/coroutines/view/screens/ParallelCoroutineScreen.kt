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
import cmps312.coroutines.viewmodel.StockQuotesViewModel
import kotlinx.coroutines.launch

@Composable
fun ParallelCoroutineScreen() {
    val viewModel = viewModel<StockQuotesViewModel>()

    Scaffold(
        topBar = { TopBar("Sequential vs. Parallel Coroutines") }
    ) {
        Column(
            Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = viewModel.selectedCompanies.value,
                onValueChange = { viewModel.selectedCompanies.value = it },
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
                    checked = viewModel.runJobsInParallel.value,
                    onCheckedChange = { viewModel.runJobsInParallel.value = it }
                )
                Button(
                    modifier = Modifier.weight(1F),
                    onClick = {
                        viewModel.onGetStockQuotes()
                    }
                ) {
                    Text(text = "Get Stock Prices")
                }
            }

            if (viewModel.jobStatusGetStockQuotes.value == JobState.RUNNING)
                CircularProgressIndicator()

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(viewModel.companyStockQuotes) {
                    Text(text = "$it")
                }
            }

            if (viewModel.jobStatusGetStockQuotes.value == JobState.CANCELLED)
                Text(text = "Job cancelled due to: ${viewModel.errorMessage.value}")

            if (viewModel.jobStatusGetStockQuotes.value == JobState.SUCCESS
                && viewModel.executionDuration.value > 0)
                Text(text = "Total execution time: ${viewModel.executionDuration.value / 1000}s")

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