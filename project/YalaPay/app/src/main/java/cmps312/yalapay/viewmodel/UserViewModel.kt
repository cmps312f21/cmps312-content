package cmps312.yalapay.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import cmps312.yalapay.repository.UserRepository

class UserViewModel (appContext: Application) : AndroidViewModel(appContext) {
    private val userRepository = UserRepository(appContext)
    fun login(email: String, password: String) = userRepository.login(email, password)
}
