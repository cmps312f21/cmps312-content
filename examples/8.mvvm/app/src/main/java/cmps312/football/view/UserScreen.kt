package cmps312.football.view


import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.football.viewmodel.UserViewModel

@Composable
fun UserScreen() {
    val userViewModel = viewModel<UserViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    var username by remember { mutableStateOf("") }

    Column (verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Row (horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = {
                    Text ("Username")
                }
            )
            Button(modifier = Modifier.padding(top = 16.dp),
                   onClick = {
                       userViewModel.addUser(username)
                       username = ""
                   }
            ) {
                Text("Add")
            }
        }
        Text("Users:")
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            for (user in userViewModel.users) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = user, modifier  = Modifier.weight(2F))
                    Button(
                        modifier  = Modifier.weight(1F),
                        onClick = { userViewModel.setCurrentUser(user) }
                    ) {
                        Text("Switch User")
                    }
                }
            }
        }
    }

    // Watch the Composable Lifecycle
    // This function is called when the Composable enters the Composition
    LaunchedEffect(Unit) {
        Log.d("LifeCycle->Compose", "onActive UserScreen.")
    }
    DisposableEffect(Unit) {
        // onDispose() is called when the Composable leaves the composition
        onDispose {
            Log.d("LifeCycle->Compose", "onDispose UserScreen.")
        }
    }
}