package cmps312.yalapay.view.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.yalapay.R
import cmps312.yalapay.view.components.displayMessage
import cmps312.yalapay.viewmodel.UserViewModel

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    var email by remember { mutableStateOf("sara@yalapay.com") }
    var password by remember { mutableStateOf("pass123") }
    var passwordVisibility by remember { mutableStateOf(false) }

    var errorMsg by remember { mutableStateOf("") }
    val userViewModel = viewModel<UserViewModel>()
    val context = LocalContext.current

    Surface(modifier = Modifier.height(600.dp).fillMaxWidth(),
        shape = RoundedCornerShape(60.dp).copy(ZeroCornerSize, ZeroCornerSize)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "Logo",
                modifier = Modifier.size(250.dp).padding(top = 60.dp)
            )

            Spacer(modifier = Modifier.padding(16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    errorMsg = ""
                },
                label = { Text("Email address", fontSize = 18.sp) },
                trailingIcon = {
                    IconButton(onClick = {

                    }, enabled = false) {
                        Icon(imageVector = Icons.Filled.Email, "")
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                placeholder = { Text("Email") },
                singleLine = true,
                isError = (errorMsg.isNotEmpty())
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    errorMsg = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp).padding(top = 5.dp),
                label = { Text("Password", fontSize = 18.sp) },
                placeholder = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                isError = (errorMsg.isNotEmpty()),
                visualTransformation = if (passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisibility)
                        Icons.Filled.VisibilityOff
                    else Icons.Filled.Visibility
                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(imageVector = image, "")
                    }
                },

                )
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            Button(
                onClick = {
                    errorMsg = ""
                    try {
                        val user = userViewModel.login(email, password)
                        displayMessage(context, "Welcome ${user.toString()}")
                        onLogin()
                    } catch (e: Exception) {
                        errorMsg = e.message ?: "Login failed"
                    }
                },
                modifier = Modifier.height(48.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Login", fontSize = 20.sp)
            }
            Text(
                text = errorMsg,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 7.dp)
            )
        }
    }
}