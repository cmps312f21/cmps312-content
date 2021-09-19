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
import cmps312.navigation.ui.profile.ProfileDetailsScreen

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
        startDestination = Screen.Profile.route,

        //Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding)) {

        // Define the app Navigation Graph
        // = possible routes a user can take through the app
        composable(Screen.Profile.route) {
            ProfileScreen(onNavigateToDetails = { userId ->
                navController.navigate( "${Screen.ProfileDetails.route}/$userId")
            })
        }

        composable("${Screen.ProfileDetails.route}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Extract the Nav arguments from the Nav BackStackEntry
            backStackEntry.arguments?.getInt("userId")?.let { userId ->
                ProfileDetailsScreen(userId = userId,
                    onNavigateBack = { navController.navigate(Screen.Profile.route) })
            }
        }

        composable(Screen.Search.route) {
            SearchScreen()
        }

        composable(Screen.Apps.route) {
            // Example screen that demonstrates how to start activities from other apps
            ExternalAppScreen()
        }
    }
}