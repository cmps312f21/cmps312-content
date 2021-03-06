package cmps312.navigation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cmps312.navigation.ui.Screen
import cmps312.navigation.ui.common.displayMessage
import cmps312.navigation.ui.quran.SurahScreen
import cmps312.navigation.ui.quran.SurahViewModel
import cmps312.navigation.ui.quran.VersesScreen
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
    val surahViewModel = viewModel<SurahViewModel>()
    displayMessage(LocalContext.current, "Surahs count: ${surahViewModel.surahs.size}")
    NavHost(
        navController = navController,
        //set the start destination as home
        startDestination = Screen.Quran.route,
        //Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding)) {

        /* Define the app Navigation Graph
           = possible routes a user can take through the app
            "quran" -> SurahScreen
            "settings" -> SettingsScreen
            "search" -> SearchScreen
        */
        composable(Screen.Quran.route) {
            /* Load the SurahScreen and when a surah is click then navigate to the verses
            screen and pass the select surahId as a parameter */
            // verses/2
            SurahScreen(onSelectSurah = { surahId ->
                navController.navigate("${Screen.Verses.route}/$surahId")
            })
        }

        // verses route receives the surahId as a parameter
        composable("${Screen.Verses.route}/{surahId}",
            arguments = listOf(navArgument("surahId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Extract the Nav arguments from the Nav BackStackEntry
            backStackEntry.arguments?.getInt("surahId")?.let { surahId ->
                /* Load the VersesScreen and when the user clicks the back arrow then navigate up */
                VersesScreen(surahId = surahId,
                    onNavigateBack = { navController.navigateUp() })
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