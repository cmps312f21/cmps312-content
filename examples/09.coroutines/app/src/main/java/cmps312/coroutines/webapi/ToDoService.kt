package cmps312.coroutines.webapi

import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class ToDo (
    val id: Int = 0,
    val title: String,
    val userId: Int = 1,
    val completed: Boolean = false)

object ToDoService {
    const val BASE_URL = "https://jsonplaceholder.typicode.com/todos"

    private val client = HttpClient() {
        //This will auto-parse from/to json when sending and receiving data from the Web API
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        //Log HTTP request/response details for debugging
        install(Logging) { level = LogLevel.ALL }
    }

    suspend fun getToDos(): List<ToDo> {
        return client.get(BASE_URL)
    }

    suspend fun getToDo(toDoId: Int): ToDo {
        val url = "$BASE_URL/$toDoId"
        return client.get(url)
    }

    suspend fun addToDo(toDo: ToDo): ToDo {
        return client.post(BASE_URL) {
            contentType(ContentType.Application.Json)
            body = toDo
        }
    }

    suspend fun updateToDo(toDo: ToDo): ToDo {
        val url = "$BASE_URL/${toDo.id}"
        return client.put(url) {
            contentType(ContentType.Application.Json)
            body = toDo
        }
    }

    suspend fun deleteToDo(toDoId: Int): Boolean {
        val url = "$BASE_URL/$toDoId"
        val response = client.delete<HttpResponse>(url)
        return (response.status == HttpStatusCode.OK)
    }
}