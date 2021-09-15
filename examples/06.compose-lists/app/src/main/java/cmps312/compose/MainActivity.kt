package cmps312.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cmps312.compose.surah.view.SurahListScreen
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
    val currentScreen by remember {
        mutableStateOf(NavigationItem.SurahList.route)
    }
    Scaffold(
        bottomBar = {
            BottomBar(currentScreen, onRouteChange = {
                navController.navigate(it)
            })
        }
    ) { innerPadding ->
        /* It is important to set the padding to innerPadding so that
           the bottom of the main content does not get hidden by the bottom bar
        */
        AppNavigator(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@Composable
fun AppNavigator(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController,
        startDestination = NavigationItem.SurahList.route,
        modifier = modifier.fillMaxSize()
    ){
        composable(NavigationItem.SurahList.route) {
            SurahListScreen()
        }
        composable(NavigationItem.SurahList.route) {
            SurahListScreen()
        }
    }
}

@Composable
fun BottomBar(currentScreen: String, onRouteChange: (String) -> Unit) {
    val items = listOf(
        NavigationItem.SurahList,
    )
    BottomNavigation {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title) },
                //selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.5f),
                alwaysShowLabel = true,
                selected = (item.route == currentScreen),
                onClick = {
                    onRouteChange(item.route)
                }
            )
        }
    }
}