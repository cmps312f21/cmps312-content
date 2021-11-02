package qu.cmps312.shoppinglist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import qa.edu.cmps312.shoppinglist.R
import qa.edu.cmps312.shoppinglist.ui.theme.ShoppingListTheme
import qu.cmps312.shoppinglist.view.MainScreen
import qu.cmps312.shoppinglist.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    companion object {
        // Choose an arbitrary request code value
        private const val RC_SIGN_IN = 123
    }

    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //authViewModel.errorMessage.observe(this) {
        if (authViewModel.errorMessage != null) {
            Toast.makeText(this, authViewModel.errorMessage, Toast.LENGTH_LONG).show()
        }
            //}

        // Get further details from Firestore about current user
        authViewModel.setCurrentUser()

        // Every time the Auth state changes display a message
        Firebase.auth.addAuthStateListener {
            println(">> Debug: Firebase.auth.addAuthStateListener: ${it.currentUser?.email}")
            val message = if (it.currentUser != null)
                "Welcome ${it.currentUser!!.displayName}"
            else
                "Sign out successful"

            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
        }

        authViewModel.signIn("erradi@live.com", "pass123")

        setContent {
            ShoppingListTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }

    private fun startSignIn() {
        // You can add more providers such as Facebook, Twitter, Github, etc.
        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        // Sign in with FirebaseUI
        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.drawable.img_shopping_list_logo)
            .setIsSmartLockEnabled(false)
            .build()
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = Firebase.auth.currentUser
                // Notify all the observers watching the current user change
                authViewModel.setCurrentUser()

                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.

                Toast.makeText(this, response?.error?.message, Toast.LENGTH_LONG).show()
                println(">> Debug: login failed: ${response?.error?.errorCode} - ${response?.error?.message}")
                return
            }
        }
    }
}