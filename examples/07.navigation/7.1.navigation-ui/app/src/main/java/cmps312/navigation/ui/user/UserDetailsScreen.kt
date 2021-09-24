package cmps312.navigation.ui.user

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun UserDetailsScreen(userId: Int = 0, onNavigateBack: () -> Unit) {
    /* Get an instance of the shared viewModel
    Make the activity the store owner of the viewModel
    to ensure that the same viewModel instance is used for all screens */
    val userViewModel = viewModel<UserViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val profile = userViewModel.getUser(userId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = MaterialTheme.colors.primarySurface
            )
            if (profile != null) {
                Text(text = "Profile ${profile.userId} - ${profile.name}")
            }
        }
    }
}