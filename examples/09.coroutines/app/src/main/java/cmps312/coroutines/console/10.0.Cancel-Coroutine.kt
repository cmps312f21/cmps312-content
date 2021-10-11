package cmps312.coroutines.console

import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import cmps312.coroutines.viewmodel.MainViewModel


suspend fun main() {
    val job = CoroutineScope(Dispatchers.Default).launch {
        fibonacci().collect {
            print("$it, ")
        }
    }

    // Cancel the job after 5 seconds
    delay(5000)
    job.cancel()

    job.invokeOnCompletion {
        if (job.isCancelled)
            println("\n>>> Job cancelled <<<")
        else
            println("\n>>> Job done <<<")
    }
    // Wait for the job to finish otherwise main will exit
    job.join()
}

// 0, 1, 1, 2, 3, 5,
fun fibonacci() = flow {
    var terms = Pair(0L, 1L)
    // this sequence is infinite
    while (true) {
        yield()  // periodic check - if job cancelled exit the loop
        emit(terms.first)
        terms = Pair(terms.second, terms.first + terms.second)
        // Suspend the function for 400ms
        delay(400)
    }
}
