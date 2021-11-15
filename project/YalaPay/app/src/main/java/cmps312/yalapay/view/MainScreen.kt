package cmps312.yalapay.view

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
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
/*        topBar = {
            if (currentRoute != Screen.LoginScreen.route) {
                TopBar(title, onNavigateBack)
            }
        },*/
        bottomBar = { AppBottomBar(navHostController, currentRoute) },
        drawerContent = {
            if (currentRoute != Screen.LoginScreen.route) {
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
    if (currentRoute != Screen.LoginScreen.route) {
        val bottomNavItems = listOf(
            Screen.DashboardScreen,
            Screen.CustomersList,
            Screen.InvoicesList,
            Screen.ChequeList,
            Screen.ChequeDepositList
        )
        BottomNavigation() {
            bottomNavItems.forEach { screen ->
                BottomNavigationItem(
                    selected = screen.route == currentRoute,
                    onClick = {
                        navController.navigate(screen.route)
                    },
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = screen.title
                        )
                    },
                    alwaysShowLabel = false
                )
            }
        }
    }
}
