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
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cmps312.compose.surah.view.SurahListScreen
import cmps312.compose.surah.view.SurahLazyListScreen
import cmps312.compose.ui.theme.ComposeListsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeListsTheme {
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
    NavHost(navController, startDestination = NavigationItem.SurahLazyList.route) {
        composable(NavigationItem.SurahList.route) {
            SurahListScreen()
        }
        composable(NavigationItem.SurahLazyList.route) {
            SurahLazyListScreen()
        }
    }
}

@Composable
fun TopBar(onRouteChange: (String) -> Unit) {
   TopAppBar(
        title = { Text("Compose Lists")},
        actions = {
            IconButton(onClick = { onRouteChange(NavigationItem.SurahList.route) }) {
                Icon(painterResource(id = NavigationItem.SurahList.icon!!), "Quran Surahs")
            }

            TopBarMenu(onRouteChange)
        }
   )
}

@Composable
fun TopBarMenu(onRouteChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val menuItems = listOf(
        NavigationItem.SurahList,
        NavigationItem.SurahLazyList
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
