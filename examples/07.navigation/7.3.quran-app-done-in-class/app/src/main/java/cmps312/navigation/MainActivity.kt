package cmps312.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cmps312.navigation.ui.common.displayMessage
import cmps312.navigation.ui.common.getCurrentRoute
import cmps312.navigation.ui.quran.SurahScreen
import cmps312.navigation.ui.quran.VersesScreen
import cmps312.navigation.ui.screens.SearchScreen
import cmps312.navigation.ui.screens.SettingsScreen
import cmps312.navigation.ui.theme.NavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                    //SurahScreen(onSelectSurah = {})
                    //SurahVersesScreen(surahId = 2, onNavigateBack = {})
                    //SettingsScreen()
                    //SearchScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    //remember navController so it does not get recreated on recomposition
    val navController = rememberNavController()

    // For debugging - display the current route everytime the route changes
    val currentRoute = getCurrentRoute(navController) ?: ""
    displayMessage(LocalContext.current, currentRoute)

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) {
        AppNavigator(navController)
    }
}

@Composable
fun AppNavigator(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "quran") {
        // Create the app NavGraph = possible routes in my app
        // "quran" -> SurahScreen
        // "settings" -> SettingsScreen
        // "search" -> SearchScreen
        composable(route = "quran") {
            /* Load the SurahScreen and when a surah is click then navigate to the verses
              screen and pass the select surahId as a parameter */
            SurahScreen(onSelectSurah = { surahId ->
                navController.navigate("verses/$surahId")
            })
        }

        // verses route receives the surahId as a parameter
        composable("verses/{surahId}",
            arguments = listOf(navArgument("surahId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Extract the Nav arguments from the current Nav BackStackEntry
            backStackEntry.arguments?.getInt("surahId")?.let { surahId ->
                /*
                Load the VersesScreen and when the user clicks the back arrow then navigate up
                */
                VersesScreen(surahId = surahId,
                    onNavigateBack = { navController.navigateUp() })
            }
        }

        composable(route = "settings") {
            SettingsScreen()
        }

        composable(route = "search") {
            SearchScreen()
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val currentRoute = getCurrentRoute(navController) ?: ""
    BottomAppBar {
        BottomNavigationItem(
            //if currentRoute is equal to the nav item route then set selected to true
            selected = currentRoute == "quran",
            onClick = {
                navController.navigate("quran")
            },
            icon = {
                // For each screen either an icon or vector resource is provided
                val icon = ImageVector.vectorResource(R.drawable.ic_quran)
                Icon(imageVector = icon, contentDescription = "Quran")
            },
            label = {
                Text(text = "Quran")
            },
            alwaysShowLabel = false
        )

        BottomNavigationItem(
            //if currentRoute is equal to the nav item route then set selected to true
            selected = currentRoute == "settings",
            onClick = {
                navController.navigate("settings")
            },
            icon = {
                val icon = Icons.Outlined.Settings
                Icon(imageVector = icon, contentDescription = "Settings")
            },
            label = {
                Text(text = "Settings")
            },
            alwaysShowLabel = false
        )

        BottomNavigationItem(
            //if currentRoute is equal to the nav item route then set selected to true
            selected = currentRoute == "search",
            onClick = {
                navController.navigate("search")
            },
            icon = {
                val icon = Icons.Outlined.Search
                Icon(imageVector = icon, contentDescription = "Search")
            },
            label = {
                Text(text = "Search")
            },
            alwaysShowLabel = false
        )
    }
}