package cmps312.navigation.ui

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cmps312.navigation.ui.components.Dropdown
import cmps312.navigation.ui.viewmodel.ProfileViewModel
import cmps312.navigation.ui.viewmodel.User

@Composable
fun HomeScreen(onNavigateToDetails: (Int) -> Unit) {
    /* Get an instance of the shared viewModel
       Make the activity the store owner of the viewModel
       to ensure that the same viewModel instance is used for all destinations
    */
    val profileViewModel = viewModel<ProfileViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    var selectedUserId by remember {
        mutableStateOf(0)
    }

    var selectedUserName by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "home",
            tint = MaterialTheme.colors.primarySurface
        )
        /*OutlinedTextField(
            value = selectedUserId.toString(),
            onValueChange = { selectedUserId = it.toInt() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )*/
        // .associate Converts a list to a map
        val options = profileViewModel.users.associate { Pair(it.userId, "${it.userId} ${it.name}") }
        Text(text = "Users count ${profileViewModel.usersCount}")
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