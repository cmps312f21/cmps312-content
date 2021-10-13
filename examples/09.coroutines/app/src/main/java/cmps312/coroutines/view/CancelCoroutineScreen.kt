package cmps312.coroutines.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.coroutines.viewmodel.MainViewModel

@Composable
fun CancelCoroutineScreen() {
    var result by remember { mutableStateOf("") }
    val viewModel = viewModel<MainViewModel>()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cancel Coroutine",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }) {
        Column(
            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = viewModel.nextValue.value.toString())

            Button(
                onClick = {
                    viewModel.startFibonacci()
                }
            ) {
                Text(text = "Fibonacci")
            }

            Button(
                onClick = {
                    viewModel.cancelFibonacci()
                }
            ) {
                Text(text = "Cancel Fibonacci")
            }
        }
    }
}
