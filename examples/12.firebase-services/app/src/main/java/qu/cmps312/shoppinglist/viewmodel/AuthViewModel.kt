package qu.cmps312.shoppinglist.viewmodel

import androidx.lifecycle.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import qu.cmps312.shoppinglist.entity.User
import qu.cmps312.shoppinglist.repository.AuthRepository

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    private val _currentUser = MutableLiveData<User?>()
    val currentUser = _currentUser as LiveData<User?>

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage = _errorMessage as LiveData<String>

    private val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        _errorMessage.value = exception.message
        println(">> Debug: Exception thrown: $exception.")
    }

    fun signUp(user: User) = viewModelScope.launch(exceptionHandler) {
        _currentUser.value = authRepository.signUp(user)
    }

    fun signIn(email: String, password: String) = viewModelScope.launch(exceptionHandler) {
        _currentUser.value = authRepository.signIn(email, password)
    }

    fun setCurrentUser() = viewModelScope.launch(exceptionHandler) {
        val uid = Firebase.auth.currentUser?.uid
        if (uid == null)
            _currentUser.value = null
        else {
            // Get further user details from Firestore
            var user = authRepository.getUser(uid)
            // If no further user details available from Firestore use
            // the basic user details from Firebase.auth.currentUser
            if (user == null) {
                val displayName = Firebase.auth.currentUser?.displayName ?: ""
                val email = Firebase.auth.currentUser?.email ?: ""
                user = User(uid, displayName, email)
            }
            _currentUser.value = user
        }
    }

    fun signOut() {
        Firebase.auth.signOut()
        _currentUser.value = null
    }
}