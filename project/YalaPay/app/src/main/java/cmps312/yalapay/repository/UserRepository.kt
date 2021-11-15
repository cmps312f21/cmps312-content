package cmps312.yalapay.repository

import android.content.Context
import cmps312.yalapay.entity.User
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class UserRepository (private val context: Context) {
    private fun getUsers()  =
        Json.decodeFromString< List<User> >(readData(context,"users.json"))

    fun login(email: String, password: String): User {
        val users = getUsers()
        val user = users.find { it.email == email.trim() && it.password == password }
        if (user != null)
            return user
        else
            throw Exception("Email and/or password invalid")
    }
}