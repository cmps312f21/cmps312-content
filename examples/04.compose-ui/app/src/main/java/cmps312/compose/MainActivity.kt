package cmps312.compose

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
import cmps312.compose.state.ClicksCounterScreen
import cmps312.compose.basics.ComposeLogoScreen
import cmps312.compose.basics.HelloScreen
import cmps312.compose.modifier.clickable.ClickableTextScreen
import cmps312.compose.modifier.styling.StylingScreen
import cmps312.compose.state.WelcomeScreen
import cmps312.compose.ui.theme.ComposeUITheme

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
        AppNavigator(navController = navController)
    }
}


@Composable
fun AppNavigator(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.ComposeLogo.route) {
        composable(Screen.Hello.route) {
            HelloScreen()
        }
        composable(Screen.ClicksCounter.route) {
            ClicksCounterScreen()
        }
        composable(Screen.ComposeLogo.route) {
            ComposeLogoScreen()
        }
        composable(Screen.Welcome.route) {
            WelcomeScreen()
        }
        composable(Screen.Clickable.route) {
            ClickableTextScreen()
        }

        composable(Screen.Styling.route) {
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
        Screen.Hello,
        Screen.ComposeLogo,
        Screen.Divider,
        Screen.ClicksCounter,
        Screen.Welcome,
        Screen.Divider,
        Screen.Styling,
        Screen.Clickable
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
