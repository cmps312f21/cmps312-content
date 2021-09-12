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
import qa.edu.cmps312.compose.basics.ClicksCounterScreen
import qa.edu.cmps312.compose.basics.HelloScreen
import qa.edu.cmps312.compose.card.ComposeLogoScreen
import qa.edu.cmps312.compose.layout.weight.ResponsiveScreen
import qa.edu.cmps312.compose.modifier.clickable.CounterScreen
import qa.edu.cmps312.compose.modifier.styling.StylingScreen
import qa.edu.cmps312.compose.state.WelcomeScreen
import qa.edu.cmps312.compose.ui.theme.ComposeUITheme
import qa.edu.cmps312.compose.widgets.button.ButtonScreen

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
        composable(NavigationItem.Counter.route) {
            CounterScreen()
        }
        composable(NavigationItem.Buttons.route) {
           ButtonScreen()
        }

        composable(NavigationItem.Styling.route) {
            StylingScreen()
        }

        composable(NavigationItem.Responsive.route) {
            ResponsiveScreen()
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
        NavigationItem.Counter,
        NavigationItem.Divider,
        NavigationItem.Buttons,
        NavigationItem.Divider,
        NavigationItem.Styling,
        NavigationItem.Responsive
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
