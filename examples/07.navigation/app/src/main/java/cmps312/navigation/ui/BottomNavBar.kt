package cmps312.navigation.ui

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * It receives navcontroller to navigate between screens,
*/
@Composable
fun BottomNavBar(navController: NavHostController) {
    BottomNavigation {
        //observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        //observe current route to change the icon color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route

        val navItems = listOf(Screen.Profile, Screen.Search, Screen.Apps)

        //Bottom nav items we declared
        navItems.forEach { navItem ->
            BottomNavigationItem(
                //it currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route)
                },
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.title)
                },
                label = {
                    Text(text = navItem.title)
                },
                alwaysShowLabel = false
            )
        }
    }
}