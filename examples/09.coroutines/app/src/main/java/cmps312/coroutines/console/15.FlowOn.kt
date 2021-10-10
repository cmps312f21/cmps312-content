package cmps312.coroutines.console

import kotlinx.coroutines.delay
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

/*
using a flowOn operator: emit values from one thread and collect them in another
*/
val flowNews = flow {
    println("Emitting coroutine running on ${currentCoroutineContext()}")
    emit("Dar")
    delay(100)
    emit("Salam")
    delay(200)
    emit("Done")
}.flowOn(Dispatchers.IO) // The above stream will run in io dispatcher

fun main() = runBlocking {
    println("Collecting coroutine running on $coroutineContext")
    flowNews.collect {
        println("Collecting -> $it")
    }
}