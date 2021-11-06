package qu.cmps312.shoppinglist.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            Icon(imageVector = Icons.Outlined.Login, contentDescription = "login")
        }

        Spacer(modifier = Modifier.padding(10.dp))

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
                .background(
                    Color.White,
                )
                .padding(10.dp)
        ) {
            Text(
                text = "Sign In",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                )
            )
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
                label = {
                    Text(
                        text = "Password"
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f),
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = { }, modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            ) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = "Create an Account")
            Spacer(modifier = Modifier.padding(20.dp))
        }
    }
}


