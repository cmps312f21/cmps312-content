package qu.cmps312.shoppinglist.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Login
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import qu.cmps312.shoppinglist.view.components.displayMessage
import qu.cmps312.shoppinglist.viewmodel.AuthViewModel

@Composable
fun LoginScreen(onLoginSuccess: ()-> Unit) {
    val authViewModel =
        viewModel<AuthViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    // LaunchedEffect will be executed when the composable is first launched
    // If the screen recomposes, the coroutine will NOT be re-executed
    LaunchedEffect(true) {
        authViewModel.currentUser = null
    }

    var email by remember { mutableStateOf("erradi@live.com") }
    var password by remember { mutableStateOf("pass123") }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f)
                .clip(
                    RoundedCornerShape(
                        bottomEnd = 30.dp,
                        bottomStart = 30.dp,
                        topEnd = 30.dp,
                        topStart = 30.dp,
                    ),
                )
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(imageVector = Icons.Outlined.Login, contentDescription = "login")
                Text(
                    text = "Sign In",
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = {
                    Text(
                        text = "Email"
                    )
                },
                placeholder = { Text(text = "Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f),
            )
            Spacer(modifier = Modifier.padding(10.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(text = "Password") },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f),
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = {
                    authViewModel.signIn(email, password)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            ) {
                Text(text = "Login")
            }

            if (authViewModel.currentUser != null)
                onLoginSuccess()

            if (authViewModel.errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = authViewModel.errorMessage, style = TextStyle(color = Color.Red))
                displayMessage(message = authViewModel.errorMessage)
            }

            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "Signup",
                style = TextStyle(color = Color.Blue, textDecoration = TextDecoration.Underline),
                modifier = Modifier.clickable {

                }
            )
        }
    }
}
