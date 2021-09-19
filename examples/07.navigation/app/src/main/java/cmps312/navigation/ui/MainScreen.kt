package cmps312.navigation.ui

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cmps312.navigation.ui.viewmodel.ProfileViewModel
import cmps312.navigation.ui.viewmodel.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun displayMessage(context: Context, message: String) {
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
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
            val currentRoute = currentRoute(navController) ?: ""
            displayMessage(LocalContext.current, currentRoute)
            // Hide the TopBar for the Profile Screen
            if (!currentRoute.startsWith(Screen.ProfileDetails.route)) {
                TopBar(coroutineScope, scaffoldState)
            }
        },
        bottomBar = { BottomNavBar(navController) },
        drawerContent = { NavDrawer() },
        floatingActionButton = { FloatingButton() }
    ) {
        //MainScreen()
        paddingValues -> AppNavigator(navController = navController, padding = paddingValues)
    }
}

@Composable
fun FloatingButton() {
    /* Get an instance of the shared viewModel
       Make the activity the store owner of the viewModel
       to ensure that the same viewModel instance is used for all destinations
    */
    val profileViewModel = viewModel<ProfileViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val context = LocalContext.current
    FloatingActionButton(
        backgroundColor = MaterialTheme.colors.primarySurface,
        onClick = {
            profileViewModel.addUser(User(0, "FN", "Added by FAB", "test@test.com"))
            displayMessage(context, "A new user added. Users count ${profileViewModel.usersCount}")
        }) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            tint = Color.White
        )
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

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun NavDrawer() {
    Column(Modifier.fillMaxSize()) {
        //Repeat is a loop which takes count as argument
        repeat(5) { item ->
            Text(text = "Item number $item", modifier = Modifier.padding(8.dp), color = Color.Black)
        }
    }
}
