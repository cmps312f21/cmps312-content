package cmps312.coroutines.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

private const val TAG = "ViewModel.Coroutines"
data class StockQuote(val name: String, val symbol: String, val price: Int)

class MainViewModel : ViewModel() {
    var _nextValue = mutableStateOf(0L)
    val nextValue : State<Long> = _nextValue

    var job : Job? = null

    fun startFibonacci() {
        stopFibonacci()
        job = viewModelScope.launch {
            fibonacci()
        }
    }

    fun stopFibonacci() {
        job?.let {
            if (it.isActive)
                it.cancel()
        }
    }

    /*
     The Fibonacci series is a series where the next term is the sum of pervious two terms.
     Fn = Fn-1 + Fn-2
     The first two terms of the Fibonacci sequence is 0 followed by 1.
    The Fibonacci sequence: 0, 1, 1, 2, 3, 5, 8, 13, 21, ...
    */
    suspend fun fibonacci() = withContext(Dispatchers.Default) {
        try {
            // 0, 1, 2, 3, 5, 8
            var terms = Pair(0L, 1L)
            // this sequence is infinite
            while (true) {
                //yield()  // periodic check - if job cancelled exit the loop
                ensureActive()
                //if (!isActive) return@withContext
                //println("${terms.first}")
                _nextValue.value = terms.first!!
                terms = Pair(terms.second, terms.first + terms.second)
                // Suspend the function for 400ms
                delay(1000)
            }
            println("Job done!")
        } catch (e: CancellationException) {
            println("Job cancelled!")
        }
    }

    // When add is called the main thread will block
    // This is a Blocking call = Synchronous call
    // val sum = add(20, 30)
    fun add(x: Int, y: Int) = x + y

    suspend fun getStockSymbol(name: String): String {
        println("getStockSymbol($name) started...")
        //Suspend for 1500ms
        delay(1500)
        val symbol = companies[name.trim()]
        println("~~getStockSymbol($name) result: $symbol")
        return symbol!!
    }

    suspend fun getPrice(symbol: String): Int {
        println("getPrice($symbol) started...")
        //Suspend for 2000ms
        delay(2000)
        val price = (50..500).random()
        println("~~getPrice($symbol) result: $price")
        return price
    }

    suspend fun getStockQuote(name: String) = withContext(Dispatchers.IO) {
        if (!companies.containsKey(name.trim())) throw Exception("Getting stock quote failed. '$name' does not exit.")
        val symbol = getStockSymbol(name)
        val price = getPrice(symbol)
        StockQuote(name.trim(), symbol, price)
    }

    val companies = mapOf(
        "Apple" to "AAPL",
        "Amazon" to  "AMZN",
        "Alibaba" to "BABA",
        "Salesforce"  to "CRM",
        "Facebook" to "FB",
        "Google" to "GOOGL",
        "IBM" to "IBM",
        "Johnson & Johnson" to "JNJ",
        "Microsoft" to "MSFT",
        "Tesla" to "TSLA"
    )
}


/*
    suspend fun processInParallel(companies : List<String>) = liveData {
        println("Debug: processInParallel Running on ${Thread.currentThread()} thread.")
        //companies.asSequence().forEach {
        coroutineScope {
            val deferred = mutableListOf<Deferred<StockQuote>>()
            for(it in companies)
                deferred.add( async { getStockQuote(it) } )
            /*val deferred = companies.asSequence().map {
                async { getStockQuote(it) }
            }*/
            deferred.forEach {
                val quote = it.await()
                emit("${quote.name} (${quote.symbol}) = ${quote.price}")
            }
        }
    }

    suspend fun processSequentially(companies : List<String>) = flow {
        companies.forEach {
            coroutineScope {
                val price = getStockQuote(it)
                emit("$it = $price")
            }
        }
    }
 */