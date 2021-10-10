package cmps312.coroutines.console

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//  Like coroutines, a flow can be canceled in a suspending function
fun main() = runBlocking {
    val job = launch {
        numsFlow.collect {
            println("Got item -> $it")
        }
    }
    delay(400)
    job.cancel()
    println("Done")
}

val numsFlow = flow {
    for (i in 1..10) {
        delay(100)
        emit(i)
    }
}