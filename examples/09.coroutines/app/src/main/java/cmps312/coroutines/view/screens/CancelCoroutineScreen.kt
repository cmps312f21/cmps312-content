package cmps312.coroutines.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.coroutines.view.components.TopBar
import cmps312.coroutines.viewmodel.FibonacciViewModel

@Composable
fun CancelCoroutineScreen() {
    val viewModel = viewModel<FibonacciViewModel>()

    Scaffold(
        topBar = { TopBar("Cancel Coroutine") }
    ) {
        Column(
            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = viewModel.nextFibonacciValue.toString())

            Button(
                onClick = {
                    if (!viewModel.isJobRunning)
                        viewModel.startFibonacci()
                    else
                        viewModel.cancelFibonacci()
                }
            ) {
                val buttonText = if (!viewModel.isJobRunning) "Start Fibonacci" else "Cancel Fibonacci"
                Text(text = buttonText)
            }
        }
    }
}
