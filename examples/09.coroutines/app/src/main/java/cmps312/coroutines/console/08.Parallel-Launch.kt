package cmps312.coroutines.console

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
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
}