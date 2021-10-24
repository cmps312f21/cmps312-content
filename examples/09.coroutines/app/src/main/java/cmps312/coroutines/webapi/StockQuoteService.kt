package cmps312.coroutines.webapi

import android.os.Build
import androidx.annotation.RequiresApi
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.features.logging.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object StockQuoteService {
    const val BASE_URL = "https://api.polygon.io/v1/open-close"
    const val API_KEY = "Jjtxe7HOP_ZjzWK3kwYQu2ovpzxTPEIp"
    private val client = HttpClient() {
        //This will auto-parse from/to json when sending and receiving data from the Web API
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                json = kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                }
            )
        }
        //Log HTTP request/response details for debugging
        //Log HTTP request/response details for debugging
        install(Logging) { level = LogLevel.ALL }
    }

    /* This method will be used to get a Stock Quote from
   https://api.polygon.io/v1/open-close/Symbol/Date?apiKey=API_KEY
   Example call: getStockQuote("IBM", "2021-10-08")
   This will get https://api.polygon.io/v1/open-close/IBM/2021-10-08?apiKey=Jjtxe7HOP_ZjzWK3kwYQu2ovpzxTPEIp
   More info about Ktor client @ https://ktor.io/docs/request.html
    */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getStockQuote(symbol: String): MarketStockQuote {
        /*val client = HttpClient()
        val url = "$BASE_URL/$symbol/$date?apiKey=$API_KEY"
        val response: String = client.get(url)
        return Json { ignoreUnknownKeys = true }.decodeFromString(response)*/

        // Set the date to current date - 1 day
        var yesterday = LocalDateTime.now().minusDays(2)
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = yesterday.format(dateFormat)

        val url = "$BASE_URL/$symbol/$date?apiKey=$API_KEY"
        println(">>> Debug: getStockQuote.url: $url")
        return client.get(url)
    }
}