package cmps312.navigation.ui

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cmps312.navigation.ui.viewmodel.ProfileViewModel
import cmps312.navigation.ui.viewmodel.User
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
            if (!currentRoute.startsWith(Screen.Profile.route)) {
                TopBar(
                    //When menu is clicked open the drawer in coroutine scope
                    onDrawerIconClicked = {
                        coroutineScope.launch {
                            //to close use -> scaffoldState.drawerState.close()
                            scaffoldState.drawerState.open()
                        }
                    })
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
fun TopBar(onDrawerIconClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Scaffold")
        },
        //Provide the navigation Icon ( Icon on the left to toggle drawer)
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",

                modifier = Modifier.clickable(onClick = onDrawerIconClicked), //When clicked trigger onClick Callback to trigger drawer open
                //tint = Color.White
            )
        },
    )
}

/**
 * It receives navcontroller to navigate between screens,
 */
@Composable
fun BottomNavBar(navController: NavHostController) {
    BottomNavigation {
        //observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        //observe current route to change the icon color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route

        val navItems = listOf(Screen.Home, Screen.Search, Screen.Apps)

        //Bottom nav items we declared
        navItems.forEach { navItem ->
            BottomNavigationItem(
                //it currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route)
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

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

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
        startDestination = Screen.Home.route,

        //Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding)) {

        // Define the app Navigation Graph
        // = possible routes a user can take through the app
        composable(Screen.Home.route) {
            HomeScreen(onNavigateToDetails = { userId ->
                navController.navigate( "${Screen.Profile.route}/$userId")
            })
        }

        composable(Screen.Search.route) {
            SearchScreen()
        }

        composable("${Screen.Profile.route}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Extract the Nav arguments from the Nav BackStackEntry
            backStackEntry.arguments?.getInt("userId")?.let { userId ->
                ProfileScreen(userId = userId,
                    onNavigateHome = { navController.navigate(Screen.Home.route) })
            }
        }

        composable(Screen.Apps.route) {
            // Example screen that demonstrates how to start activities from other apps
            ExternalAppScreen()
        }
    }
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

/*
@Preview
@Composable
fun ScaffoldExamplePreview() {
    MainScreen()
}
*/

/*
@Composable
fun MainScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Body Content")
    }
} */