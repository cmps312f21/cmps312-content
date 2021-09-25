package cmps312.navigation.ui

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.ViewModelStore
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cmps312.navigation.ui.Screen
import cmps312.navigation.ui.common.*

@Composable
fun MainScreen() {
    //remember navController so it does not get recreated on recomposition
    val navController = rememberNavController()
    // For debugging - display the current route everytime the route changes
    val currentRoute = getCurrentRoute(navController) ?: ""
    displayMessage(LocalContext.current, currentRoute)

    Scaffold(
        topBar = {
            // Hide the TopBar for the User Details Screen & Verses Screen
            if (!currentRoute.startsWith(Screen.Verses.route)) {
                TopBar()
            }
        },
        bottomBar = { BottomNavBar(navController) }
    ) {
        paddingValues -> AppNavigator(navController = navController, padding = paddingValues)
    }
}

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