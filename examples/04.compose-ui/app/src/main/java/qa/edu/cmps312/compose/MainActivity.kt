package qa.edu.cmps312.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import qa.edu.cmps312.compose.state.ClicksCounterScreen
import qa.edu.cmps312.compose.basics.ComposeLogoScreen
import qa.edu.cmps312.compose.basics.HelloScreen
import qa.edu.cmps312.compose.modifier.clickable.ClickableTextScreen
import qa.edu.cmps312.compose.modifier.styling.StylingScreen
import qa.edu.cmps312.compose.state.WelcomeScreen
import qa.edu.cmps312.compose.ui.theme.ComposeUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeUITheme {
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
    Scaffold(
        topBar = {
            TopBar(onRouteChange= {
                navController.navigate(it)
            })
        },
        //bottomBar = { BottomNavigationBar(navController) }
    ) {
        Navigation(navController = navController)
    }
}


@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.ComposeLogo.route) {
        composable(NavigationItem.Hello.route) {
            HelloScreen()
        }
        composable(NavigationItem.ClicksCounter.route) {
            ClicksCounterScreen()
        }
        composable(NavigationItem.ComposeLogo.route) {
            ComposeLogoScreen()
        }
        composable(NavigationItem.Welcome.route) {
            WelcomeScreen()
        }
        composable(NavigationItem.Clickable.route) {
            ClickableTextScreen()
        }

        composable(NavigationItem.Styling.route) {
            StylingScreen()
        }

    }
}

@Composable
fun TopBar(onRouteChange: (String) -> Unit) {
   TopAppBar(
        title = { Text("Compose UI")},
        actions = {
            TopBarMenu(onRouteChange)
        }
   )
}

@Composable
fun TopBarMenu(onRouteChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val menuItems = listOf(
        NavigationItem.Hello,
        NavigationItem.ComposeLogo,
        NavigationItem.Divider,
        NavigationItem.ClicksCounter,
        NavigationItem.Welcome,
        NavigationItem.Divider,
        NavigationItem.Styling,
        NavigationItem.Clickable
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
                        onRouteChange(menuItem.route)
                    }) {
                        Text(menuItem.title)
                    }
                }
            }
        }
    }
}
