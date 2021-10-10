package cmps312.coroutines.webapi

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

const val BASE_URL = "https://api.polygon.io/v1/open-close"
const val API_KEY = "Jjtxe7HOP_ZjzWK3kwYQu2ovpzxTPEIp"

/* This method will be used to get a Stock Quote from
   https://api.polygon.io/v1/open-close/Symbol/Date?apiKey=API_KEY
   Example call: getStockQuote("IBM", "2021-10-08")
   This will get https://api.polygon.io/v1/open-close/IBM/2021-10-08?apiKey=Jjtxe7HOP_ZjzWK3kwYQu2ovpzxTPEIp
   More info about Ktor client @ https://ktor.io/docs/request.html
*/
suspend fun getStockQuote(symbol: String, date: String): MarketStockQuote {
    val client = HttpClient()
    //val address = Url("$BASE_URL/$symbol/$date?apiKey=$API_KEY")
    val url = "$BASE_URL/$symbol/$date?apiKey=$API_KEY"
    val response: String = client.get(url)
    return Json{ ignoreUnknownKeys = true }.decodeFromString(response)
}