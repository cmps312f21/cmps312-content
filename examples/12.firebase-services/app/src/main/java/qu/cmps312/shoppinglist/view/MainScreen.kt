package qu.cmps312.shoppinglist.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import qu.cmps312.shoppinglist.repository.NavDrawer
import qu.cmps312.shoppinglist.view.components.displayMessage
import qu.cmps312.shoppinglist.view.components.getCurrentRoute
import qu.cmps312.shoppinglist.viewmodel.AuthViewModel

@ExperimentalFoundationApi
@Composable
fun MainScreen() {
    val authViewModel =
        viewModel<AuthViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    //create a scaffold state
    val scaffoldState = rememberScaffoldState()

    //Create a coroutine scope. Opening of Drawer and snackBar should happen in background thread without blocking main thread
    val coroutineScope = rememberCoroutineScope()

    //remember navController so it does not get recreated on recomposition
    val navController = rememberNavController()
    val currentRoute = getCurrentRoute(navController)
    val context = LocalContext.current
    var startDestination by remember { mutableStateOf(Screen.Login.route) }

    // LaunchedEffect will be executed when the composable is first launched
    // If the screen recomposes, the coroutine will NOT be re-executed
    LaunchedEffect(true) {
        // Get further details from Firestore about current user
        authViewModel.setCurrentUser()

        // Every time the Auth state changes display a message
        Firebase.auth.addAuthStateListener {
            println(">> Debug: Firebase.auth.addAuthStateListener: ${it.currentUser?.email}")
            val message = if (it.currentUser != null) {
                startDestination = Screen.ShoppingList.route
                "Welcome ${it.currentUser!!.displayName}"
            } else {
                "Sign out successful"
            }
            displayMessage(context, message = message)
        }
    }

    Scaffold(
        //pass the scaffold state
        scaffoldState = scaffoldState,
        topBar = {
            // Do not show Top App Bar for the login screen
            if (currentRoute != Screen.Login.route) {
                TopBar(coroutineScope, scaffoldState)
            }
        },
        bottomBar = {
            // Do not show Bottom App Bar for the login screen
            if (currentRoute != Screen.Login.route)
                BottomNavBar(navController)
        },
        drawerContent = { NavDrawer(navController, coroutineScope, scaffoldState) },
    ) { paddingValues ->
        AppNavigator(navController = navController, padding = paddingValues, startDestination = startDestination)
    }
}

/**
 * It receives navController to navigate between screens
 */
@Composable
fun BottomNavBar(navController: NavHostController) {
    BottomAppBar {
        //observe current route to change the icon color,label color when navigated
        val currentRoute = getCurrentRoute(navController)
        val navItems = listOf(Screen.ShoppingList, Screen.CloudStorage) //, Screen.Login)

        navItems.forEach { navItem ->
            BottomNavigationItem(
                //if currentRoute is equal to the nav item route then set selected to true
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        /* Navigate to the destination only if we’re not already on it,
                        avoiding multiple copies of the destination screen on the back stack */
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.title)
                },
                label = {
                    Text(text = navItem.title)
                },
                alwaysShowLabel = false
            )
        }
    }
}

//A function which will receive a callback to trigger to opening the drawer
@Composable
fun TopBar(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
    TopAppBar(
        title = {
            Text(text = "Shopping App")
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