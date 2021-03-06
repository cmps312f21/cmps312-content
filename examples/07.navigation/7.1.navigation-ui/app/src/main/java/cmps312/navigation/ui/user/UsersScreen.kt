package cmps312.navigation.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.navigation.ui.components.Dropdown
import cmps312.navigation.ui.user.User
import cmps312.navigation.ui.user.UserViewModel

@Composable
fun UsersScreen(onNavigateToDetails: (Int) -> Unit) {
    /* Get an instance of the shared viewModel
    Make the activity the store owner of the viewModel
    to ensure that the same viewModel instance is used for all screens */
    val userViewModel = viewModel<UserViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    var selectedUserId by remember {
        mutableStateOf(0)
    }

    var selectedUserName by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = { FloatingButton() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = MaterialTheme.colors.primarySurface
            )
            // .associate Converts a list to a map
            val options =
                userViewModel.users.associate { Pair(it.userId, "${it.userId} ${it.name}") }
            Text(text = "Users count ${userViewModel.usersCount}")
            Dropdown(
                label = "Select a User",
                options = options,
                selectedOption = (selectedUserId to selectedUserName),
                onSelectionChange = { (userId, userName) ->
                    run {
                        selectedUserId = userId
                        selectedUserName = userName
                    }
                })

            Spacer(modifier = Modifier.padding(8.dp))

            Button(onClick = {
                onNavigateToDetails(selectedUserId)
            }) {
                Text("Profile Details")
            }
        }
    }
}

@Composable
fun FloatingButton() {
    /* Get an instance of the shared viewModel
       Make the activity the store owner of the viewModel
       to ensure that the same viewModel instance is used for all destinations
    */
    val userViewModel = viewModel<UserViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val context = LocalContext.current
    FloatingActionButton(
        backgroundColor = MaterialTheme.colors.primarySurface,
        onClick = {
            userViewModel.addUser(User(0, "FN", "Added by FAB", "test@test.com"))
            displayMessage(context, "A new user added. Users count ${userViewModel.usersCount}")
        }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            tint = Color.White
        )
    }
}