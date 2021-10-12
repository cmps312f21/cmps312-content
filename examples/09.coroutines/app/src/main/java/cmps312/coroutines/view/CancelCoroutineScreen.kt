package cmps312.coroutines.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.coroutines.viewmodel.MainViewModel

@Composable
fun CancelCoroutineScreen() {
    var result by remember { mutableStateOf("") }
    val viewModel = viewModel<MainViewModel>()

    Column(
        modifier = Modifier.padding(top = 8.dp),
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
                viewModel.stopFibonacci()
            }
        ) {
            Text(text = "Stop Fibonacci")
        }
    }
}
