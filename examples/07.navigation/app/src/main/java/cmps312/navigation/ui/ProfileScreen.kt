package cmps312.navigation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cmps312.navigation.ui.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(navController: NavController, userId: Int = 0) {
    // Get the profile details from the viewModel
    val profileViewModel : ProfileViewModel = viewModel()
    val profile = profileViewModel?.getUser(userId)

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