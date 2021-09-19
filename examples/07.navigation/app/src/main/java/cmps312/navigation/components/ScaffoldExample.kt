package cmps312.navigation.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

fun displayMessage(context: Context, message: String) {
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}

@Composable
fun ScaffoldExample() {
    //create a scaffold state
    val scaffoldState = rememberScaffoldState()

    //Create a coroutine scope. Opening of Drawer and snackbar should happen in background thread without blocking main thread
    val coroutineScope = rememberCoroutineScope()

    //remember navController so it does not get recreated on recomposition
    val navController = rememberNavController()

    //Scaffold Composable
    Scaffold(
        //pass the scaffold state
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                //When menu is clicked open the drawer in coroutine scope
                onMenuClicked = {
                    coroutineScope.launch {
                        //to close use -> scaffoldState.drawerState.close()
                        scaffoldState.drawerState.open()
                    }
                })
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
    val context = LocalContext.current
    FloatingActionButton(
        backgroundColor = MaterialTheme.colors.primarySurface,
        onClick = {
            displayMessage(context, "FAB clicked")
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
fun TopBar(onMenuClicked: () -> Unit) {
    //TopAppBar Composable
    TopAppBar(
        //Provide Title
        title = {
            Text(text = "Scaffold", color = Color.White)
        },
        //Provide the navigation Icon ( Icon on the left to toggle drawer)
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",

                modifier = Modifier.clickable(onClick = onMenuClicked), //When clicked trigger onClick Callback to trigger drawer open
                tint = Color.White
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

        //Bottom nav items we declared
        Constants.BottomNavItems.forEach { navItem ->
            BottomNavigationItem(
                //it currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route)
                },
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                },
                label = {
                    Text(text = navItem.label)
                },
                alwaysShowLabel = false
            )
        }
    }
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
        startDestination = "home",
        //Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding)) {

        //  route : Home
        composable("home") {
            HomeScreen()
        }

        //  route : search
        composable("search") {
            SearchScreen()
        }

        //  route : profile
        composable("profile") {
            ProfileScreen()
        }
    }
}

@Composable
fun MainScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Body Content")
    }
}

@Composable
fun NavDrawer() {
    //Column Composable
    Column(Modifier.fillMaxSize()) {
        //Repeat is a loop which takes count as argument
        repeat(5) { item ->
            Text(text = "Item number $item", modifier = Modifier.padding(8.dp), color = Color.Black)
        }
    }
}

@Preview
@Composable
fun ScaffoldExamplePreview() {
    ScaffoldExample()
}