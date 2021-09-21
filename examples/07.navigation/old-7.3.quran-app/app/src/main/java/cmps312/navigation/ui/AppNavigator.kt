package cmps312.navigation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cmps312.navigation.ui.quran.SurahScreen
import cmps312.navigation.ui.quran.SurahVersesScreen
import cmps312.navigation.ui.screens.*

/**
 * It receives navcontroller to navigate between screens,
 * padding values -> Since BottomNavigation has some heights,
 * to avoid clipping of screen, we set padding provided by scaffold
 */
@Composable
fun AppNavigator(
    navController: NavHostController,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        //set the start destination as home
        startDestination = Screen.Quran.route,
        //Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding)) {

        /* Define the app Navigation Graph
           = possible routes a user can take through the app */
        composable(Screen.Quran.route) {
            SurahScreen(onSelectSurah = { surahId ->
                navController.navigate("${Screen.Verses.route}/$surahId")
            })
        }

        composable("${Screen.Verses.route}/{surahId}",
            arguments = listOf(navArgument("surahId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Extract the Nav arguments from the Nav BackStackEntry
            backStackEntry.arguments?.getInt("surahId")?.let { surahId ->
                SurahVersesScreen(surahId = surahId,
                    onNavigateBack = { navController.navigate(Screen.Quran.route) })
            }
        }

        composable(Screen.Search.route) {
            SearchScreen()
        }

        composable(Screen.Settings.route) {
            SettingsScreen()
        }
    }
}