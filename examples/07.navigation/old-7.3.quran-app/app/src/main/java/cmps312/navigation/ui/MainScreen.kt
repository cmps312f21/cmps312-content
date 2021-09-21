package cmps312.navigation.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

fun displayMessage(context: Context, message: String) {
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}

@Composable
fun getCurrentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun MainScreen() {
    //remember navController so it does not get recreated on recomposition
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            val currentRoute = getCurrentRoute(navController) ?: ""
            displayMessage(LocalContext.current, currentRoute)
            // Hide the TopBar for the User Details Screen & Verses Screen
            if (!currentRoute.startsWith(Screen.Verses.route)) {
                TopBar()
            }
        },
        bottomBar = { BottomNavBar(navController) }
    ) {
        //MainScreen()
        paddingValues -> AppNavigator(navController = navController, padding = paddingValues)
    }
}

//A function which will receive a callback to trigger to opening the drawer
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Quran App",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    )
}