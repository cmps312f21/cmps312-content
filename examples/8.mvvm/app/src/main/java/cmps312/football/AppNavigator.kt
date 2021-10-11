package cmps312.football

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cmps312.football.view.ScoreScreen
import cmps312.football.view.UserScreen

/**
 * It receives navcontroller to navigate between screens,
 * padding values -> Since BottomNavigation has some heights,
 * to avoid clipping of screen, we set padding provided by scaffold
 */
@Composable
fun AppNavigator(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        //set the start destination as home
        startDestination = Screen.Score.route,
        //Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues)) {

        /* Define the app Navigation Graph
           = possible routes a user can take through the app
           Route = String -> Screen
        */
        composable(Screen.Score.route) {
            ScoreScreen()
        }

        composable(Screen.Users.route) {
            UserScreen()
        }
    }
}