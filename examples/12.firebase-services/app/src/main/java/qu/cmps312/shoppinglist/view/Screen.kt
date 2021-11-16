package qu.cmps312.shoppinglist.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object ShoppingList : Screen(route = "shopping-list", title = "Shopping List", icon = Icons.Outlined.ShoppingCart)
    object ShoppingItem : Screen(route = "shopping-item", title = "Shopping Item", icon = Icons.Outlined.ShoppingBag)
    object CloudStorage : Screen(route = "storage", title = "Storage", icon = Icons.Outlined.CloudUpload)
    object Signup : Screen(route = "signup", title = "Signup", icon = Icons.Outlined.PersonAdd)
    object Login : Screen(route = "login", title = "Login", icon = Icons.Outlined.Login)
    object Logout : Screen(route = "", title = "Logout", icon = Icons.Outlined.Logout)
    object Divider : Screen(route = "", title = "Divider", icon = Icons.Outlined.Minimize)
}