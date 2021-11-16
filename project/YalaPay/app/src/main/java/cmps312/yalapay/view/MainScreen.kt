package cmps312.yalapay.view

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cmps312.yalapay.view.components.Drawer
import cmps312.yalapay.view.components.getCurrentRoute

@Composable
fun MainScreen() {
    val navHostController = rememberNavController()
    var currentRoute = getCurrentRoute(navHostController)

    Scaffold(
        bottomBar = { AppBottomBar(navHostController, currentRoute) },
        drawerContent = {
            if (currentRoute != Screen.Login.route) {
                Drawer(navHostController)
            }
        },
        drawerShape = RoundedCornerShape(15.dp)
    ) {
        AppNavigator(navController = navHostController, it)
    }
}

@Composable
fun AppBottomBar(navController: NavController, currentRoute: String?) {
    if (currentRoute != Screen.Login.route) {
        val bottomNavItems = listOf(
            Screen.Dashboard,
            Screen.Customers,
            Screen.Invoices,
            Screen.ChequeDeposits
        )
        BottomNavigation() {
            bottomNavItems.forEach { navItem ->
                BottomNavigationItem(
                    selected = navItem.route == currentRoute,
                    onClick = {
                        navController.navigate(navItem.route)
                    },
                    icon = {
                        Icon(
                            imageVector = navItem.icon,
                            contentDescription = navItem.title
                        )
                    },
                    label = {
                        Text(text = navItem.title)
                    },
                    alwaysShowLabel = false
                )
            }
        }
    }
}
