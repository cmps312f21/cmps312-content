package cmps312.football

import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import cmps312.football.view.common.getCurrentRoute

/**
 * It receives navcontroller to navigate between screens
*/
@Composable
fun BottomNavBar(navController: NavHostController) {
    BottomAppBar {
        //observe current route to change the icon color,label color when navigated
        val currentRoute = getCurrentRoute(navController)
        val navItems = listOf(Screen.Score, Screen.Users)

        navItems.forEach { navItem ->
            BottomNavigationItem(
                //if currentRoute is equal to the nav item route then set selected to true
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        /* Navigate to the destination only if we’re not already on it,
                        avoiding multiple copies of the destination screen on the back stack */
                        launchSingleTop = true
                    }
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