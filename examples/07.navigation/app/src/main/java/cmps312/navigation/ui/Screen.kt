package cmps312.navigation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen(route = "home", title = "Home", icon = Icons.Filled.Home)
    object Search : Screen(route = "search", title = "Search", icon = Icons.Filled.Search)
    object Profile : Screen(route = "profile", title = "Profile", icon = Icons.Filled.Person)
}