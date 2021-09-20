package cmps312.navigation.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun displayMessage(context: Context, message: String) {
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}

@Composable
fun getCurrentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun MainScreen() {
    //create a scaffold state
    val scaffoldState = rememberScaffoldState()

    //Create a coroutine scope. Opening of Drawer and snackbar should happen in background thread without blocking main thread
    val coroutineScope = rememberCoroutineScope()

    //remember navController so it does not get recreated on recomposition
    val navController = rememberNavController()

    Scaffold(
        //pass the scaffold state
        scaffoldState = scaffoldState,
        topBar = {
            val currentRoute = getCurrentRoute(navController) ?: ""
            displayMessage(LocalContext.current, currentRoute)
            // Hide the TopBar for the User Details Screen & Verses Screen
            if (!currentRoute.startsWith(Screen.UserDetails.route)) {
                TopBar(coroutineScope, scaffoldState)
            }
        },
        bottomBar = { BottomNavBar(navController) },
        drawerContent = { NavDrawer(navController, coroutineScope, scaffoldState) },
    ) {
        //MainScreen()
        paddingValues -> AppNavigator(navController = navController, padding = paddingValues)
    }
}

//A function which will receive a callback to trigger to opening the drawer
@Composable
fun TopBar(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
    TopAppBar(
        title = {
            Text(text = "Navigation")
        },
        //Provide the navigation Icon ( Icon on the left to toggle drawer)
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.clickable(onClick = {
                    //When the icon is clicked open the drawer in coroutine scope
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                })
            )
        }
    )
}