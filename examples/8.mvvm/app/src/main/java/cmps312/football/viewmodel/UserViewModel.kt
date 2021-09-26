package cmps312.football.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    private var _currentUser = mutableStateOf("")
    val users = mutableStateListOf<String>()

    val currentUser : State<String> = _currentUser

    fun addUser(username: String) {
        users.add(username)
    }

    fun setCurrentUser(username: String) {
        _currentUser.value = username
    }
}