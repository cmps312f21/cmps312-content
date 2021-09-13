package cmps312.compose.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun PasswordTextField() {
    var password by remember { mutableStateOf("") }

    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun PhoneTextField() {
    var phone by remember { mutableStateOf("") }

    TextField(
        value = phone,
        onValueChange = { phone = it },
        label = { Text("Mobile number") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )
}