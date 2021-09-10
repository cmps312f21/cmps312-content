package qa.edu.cmps312.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import qa.edu.cmps312.compose.basics.ClicksCounterScreen
import qa.edu.cmps312.compose.basics.HelloScreen
import qa.edu.cmps312.compose.card.ComposeLogoScreen
import qa.edu.cmps312.compose.lists.SurahListScreen
import qa.edu.cmps312.compose.lists.SurahsScreen
import qa.edu.cmps312.compose.modifier.clickable.CounterScreen
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
    var selectedRoute by remember {
        mutableStateOf(NavigationItem.ComposeLogo.route)
    }

    Scaffold(
        topBar = {
            TopBar(onRouteChange= {
                selectedRoute = it
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
        composable(NavigationItem.SurahList.route) {
            SurahListScreen()
        }
        composable(NavigationItem.Surahs.route) {
            SurahsScreen()
        }
    }
}

@Composable
fun TopBar(onRouteChange: (String) -> Unit) {
   TopAppBar(
        title = { Text("Compose UI")},
        actions = {
            IconButton(onClick = { onRouteChange(NavigationItem.SurahList.route) }) {
                Icon(Icons.Filled.FormatListNumbered, null)
            }

            TopBarMenu(onRouteChange)
        }
   )
}

@Composable
fun TopBarMenu(onRouteChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val menuItems = listOf(
        NavigationItem.Hello,
        NavigationItem.ClicksCounter,
        NavigationItem.ComposeLogo,
        NavigationItem.Divider,
        NavigationItem.Welcome,
        NavigationItem.Counter,
        NavigationItem.Divider,
        NavigationItem.SurahList,
        NavigationItem.Surahs
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
