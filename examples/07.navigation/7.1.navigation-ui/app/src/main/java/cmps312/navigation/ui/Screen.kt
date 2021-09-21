package cmps312.navigation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null, val iconResourceId:Int? = null) {
    object Users : Screen(route = "users", title = "Users", icon = Icons.Outlined.People)
    object UserDetails : Screen(route = "user-details", title = "User Details", icon = Icons.Outlined.Person)

    object Search : Screen(route = "search", title = "Search", icon = Icons.Outlined.Search)
    object Apps : Screen(route = "apps", title = "Apps", icon = Icons.Outlined.Link)

    //Nav Drawer items
    object Profile : Screen(route = "profile", title = "Profile", icon = Icons.Outlined.Person)
    object Addresses : Screen(route = "addresses", title = "Addresses", icon = Icons.Outlined.LocationOn)
    object Orders : Screen(route = "orders", title = "Orders", icon = Icons.Outlined.DeliveryDining)
    object Settings : Screen(route = "settings", title = "Settings", icon = Icons.Outlined.Settings)
    object FAQ : Screen(route = "faq", title = "FAQ", icon = Icons.Outlined.QuestionAnswer)
    object Divider : Screen(route = "", title = "Divider", icon = Icons.Outlined.Minimize)
}