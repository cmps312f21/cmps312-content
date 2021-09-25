package cmps312.football.view

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*

@Composable
fun LoginScreen() {
    var username by remember { mutableStateOf("user") }
    var password by remember { mutableStateOf("pass") }

    fun login() = {} //Api.login(username, password)

    BasicTextField(
        value = username,
        onValueChange = { username = it }
    )
    BasicTextField(
        value = password,
        onValueChange = { password = it }
    )
    Button(onClick = { login() }) {
        Text("Login")
    }
}