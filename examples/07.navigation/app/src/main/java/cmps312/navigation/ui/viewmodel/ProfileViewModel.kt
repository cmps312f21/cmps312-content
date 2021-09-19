package cmps312.navigation.ui.viewmodel

import androidx.lifecycle.ViewModel

data class User(val userId: Int, val firstName: String, val lastName: String, val email: String) {
    val name : String
        get() = "$firstName $lastName"
}
class ProfileViewModel : ViewModel() {
    val users = listOf(
        User(1, "Ahmed", "Faleh", "ahmed@test.com"),
        User(2, "Fatima", "Faleh", "fatima@test.com"),
        User(3, "Ali", "Saleh", "ali@test.com"),
        User(4, "Samir", "Saghir", "samir@test.com"),
        User(5, "Samira", "Saghir", "samira@test.com"),
        User(6, "Abbas", "Ibn Firnas", "abbas@test.com"),
    )

    fun getUser(userId: Int) = users.find { it.userId == userId }
}