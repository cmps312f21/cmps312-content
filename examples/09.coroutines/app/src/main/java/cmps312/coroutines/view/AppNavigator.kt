package cmps312.coroutines.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cmps312.coroutines.view.screens.CancelCoroutineScreen
import cmps312.coroutines.view.screens.StockQuotesScreen
import cmps312.coroutines.view.screens.StockQuoteScreen
import cmps312.coroutines.view.screens.WhyCoroutinesScreen

/**
 * It receives navController to navigate between screens,
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
        startDestination = Screen.WhyCoroutines.route,
        //Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding)) {

        /* Define the app Navigation Graph
           = possible routes a user can take through the app
        */
        composable(Screen.WhyCoroutines.route) {
            WhyCoroutinesScreen()
        }

        composable(Screen.CancelCoroutine.route) {
            CancelCoroutineScreen()
        }

        composable(Screen.StockQuote.route) {
            StockQuoteScreen()
        }

        composable(Screen.StockQuotes.route) {
            StockQuotesScreen()
        }
    }
}