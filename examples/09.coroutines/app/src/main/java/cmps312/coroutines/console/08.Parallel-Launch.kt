package cmps312.coroutines.console

import kotlinx.coroutines.*

suspend fun main() {
    val job = CoroutineScope(Dispatchers.Default).launch {
        launch {
            delay(1200)
            println("Hello")
        }
        launch {
            delay(600)
            println("Big")
        }
        launch {
            delay(300)
            println("Beautiful")
        }
    }

    // Wait for the job to finish otherwise the main will exit without the showing the results
    job.join()
}