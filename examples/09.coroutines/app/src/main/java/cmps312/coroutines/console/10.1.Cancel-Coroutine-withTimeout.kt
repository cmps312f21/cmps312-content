package cmps312.coroutines.console

import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import cmps312.coroutines.viewmodel.MainViewModel


suspend fun main() {
    val JOB_TIMEOUT = 5000L

    val job = CoroutineScope(Dispatchers.IO).launch {
        // Cancel the job after 5 seconds timeout
        withTimeout(JOB_TIMEOUT) {
            fibonacci().collect {
                print("$it, ")
            }
        }
    }

    job.invokeOnCompletion {
        if (job.isCancelled)
            println("\n>>> Job cancelled <<<")
        else
            println("\n>>> Job done <<<")
    }

    // Wait for the job to finish otherwise main will exit
    job.join()
}
