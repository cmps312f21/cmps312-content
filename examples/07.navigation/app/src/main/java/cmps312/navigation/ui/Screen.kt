package cmps312.navigation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen(route = "home", title = "Home", icon = Icons.Outlined.Home)
    object Search : Screen(route = "search", title = "Search", icon = Icons.Outlined.Search)
    object Profile : Screen(route = "profile", title = "Profile", icon = Icons.Outlined.Person)
    object Apps : Screen(route = "apps", title = "Apps", icon = Icons.Outlined.Link)
}