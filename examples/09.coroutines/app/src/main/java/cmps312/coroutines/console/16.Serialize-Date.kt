import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Person(val name: String, val birthDate: LocalDate)

fun main() {
    val banksString = "[\"QNB\", \"Rayyan\"]"
    val banks = Json.decodeFromString<List<String>>(banksString)
    println(banks)

    val ali = Person("Ali", LocalDate(1990, 5, 18))
    println(Json.encodeToString(ali))  // {"name":"Ali","birthDate":"1990-05-18"}
}

