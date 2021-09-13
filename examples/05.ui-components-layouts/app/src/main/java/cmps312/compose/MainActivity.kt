package cmps312.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cmps312.compose.layout.weight.ResponsiveScreen
import cmps312.compose.ui.theme.AppTheme
import cmps312.compose.components.ButtonScreen
import cmps312.compose.components.RadioButtonScreen
import cmps312.compose.components.SwitchScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var screenTitle by remember { mutableStateOf("Compose UI") }

    Scaffold(
        topBar = {
            TopBar(onRouteChange= {
                screenTitle = it.title
                navController.navigate(it.route)
            }, screenTitle)
        },
        //bottomBar = { BottomNavigationBar(navController) }
    ) {
        AppNavigator(navController = navController)
    }
}


@Composable
fun AppNavigator(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Button.route) {
        composable(NavigationItem.Button.route) {
           ButtonScreen()
        }

        composable(NavigationItem.RadioButton.route) {
            RadioButtonScreen()
        }

        composable(NavigationItem.Switch.route) {
            SwitchScreen()
        }

        composable(NavigationItem.TipCalculator.route) {
            TipCalculator()
        }

        composable(NavigationItem.Responsive.route) {
            ResponsiveScreen()
        }
    }
}

@Composable
fun TopBar(onRouteChange: (NavigationItem) -> Unit, screenTitle: String = "Compose UI") {
   TopAppBar(
        title = { Text(screenTitle)},
        actions = {
            IconButton(onClick = { onRouteChange(NavigationItem.TipCalculator) }) {
                Icon(imageVector = Icons.Outlined.AttachMoney, contentDescription = null)
            }
            TopBarMenu(onRouteChange)
        }
   )
}

@Composable
fun TopBarMenu(onRouteChange: (NavigationItem) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val menuItems = listOf(
        NavigationItem.Button,
        NavigationItem.RadioButton,
        NavigationItem.Switch,
        NavigationItem.Divider,
        NavigationItem.Responsive,
        NavigationItem.Divider,
        NavigationItem.TipCalculator
    )

    Box(Modifier.wrapContentSize(Alignment.TopEnd)) {
        IconButton(onClick = {
            expanded = true
        }) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = "More..."
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {

            menuItems.forEach { menuItem ->
                if (menuItem.title == "Divider") {
                    Divider()
                } else {
                    DropdownMenuItem(onClick = {
                        expanded = false
                        onRouteChange(menuItem)
                    }) {
                        Text(menuItem.title)
                    }
                }
            }
        }
    }
}
