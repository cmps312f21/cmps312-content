package cmps312.navigation.ui

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * It receives navcontroller to navigate between screens,
*/
@Composable
fun BottomNavBar(navController: NavHostController) {
    BottomNavigation {
        //observe current route to change the icon color,label color when navigated
        val currentRoute = getCurrentRoute(navController)

        val navItems = listOf(Screen.Users, Screen.Search, Screen.Apps)

        //Bottom nav items we declared
        navItems.forEach { navItem ->
            BottomNavigationItem(
                //it currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route)
                },
                icon = {
                    // For each screen either an icon or vector resource is provided
                    val icon = navItem.icon ?: ImageVector.vectorResource(navItem.iconResourceId!!)
                    Icon(imageVector = icon, contentDescription = navItem.title)
                },
                label = {
                    Text(text = navItem.title)
                },
                alwaysShowLabel = false
            )
        }
    }
}