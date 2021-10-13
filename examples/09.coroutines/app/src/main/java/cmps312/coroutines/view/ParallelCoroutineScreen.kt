package cmps312.coroutines.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.coroutines.viewmodel.MainViewModel
import cmps312.coroutines.viewmodel.StockQuote
import kotlinx.coroutines.launch

@Composable
fun ParallelCoroutineScreen() {
    val coroutineScope = rememberCoroutineScope()
    val viewModel = viewModel<MainViewModel>()
    var selectedCompany by remember {  mutableStateOf("") }
    var stockQuote by remember { mutableStateOf(StockQuote()) }

    Column(
        Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Dropdown(label = "Company",
            options = viewModel.companies.keys.toList(),
            selectedOption = selectedCompany,
            onSelectionChange = {
                selectedCompany = it
                coroutineScope.launch {
                    stockQuote = viewModel.getStockQuote(selectedCompany)
                }
            }
        )

        Text(text = "$stockQuote")
    }
}