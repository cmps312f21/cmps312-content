package cmps312.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cmps312.compose.components.*
import cmps312.compose.layout.ResponsiveScreen
import cmps312.compose.ui.theme.AppTheme
import cmps312.compose.layout.ArtistCardScreen
import cmps312.compose.layout.BoxLayoutScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var screenTitle by remember { mutableStateOf("Compose UI") }

    Scaffold(
        topBar = {
            TopBar(onRouteChange= {
                screenTitle = it.title
                navController.navigate(it.route)
            }, screenTitle)
        },
        //bottomBar = { BottomNavigationBar(navController) }
    ) {
        AppNavigator(navController = navController)
    }
}


@Composable
fun AppNavigator(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Button.route) {
        composable(NavigationItem.TextField.route) {
            TextFieldScreen()
        }

        composable(NavigationItem.Button.route) {
           ButtonScreen()
        }

        composable(NavigationItem.RadioButton.route) {
            RadioButtonScreen()
        }

        composable(NavigationItem.Switch.route) {
            SwitchScreen()
        }
        composable(NavigationItem.CheckBox.route) {
            CheckBoxScreen()
        }
        composable(NavigationItem.Dropdown.route) {
            DropdownScreen()
        }
        composable(NavigationItem.Slider.route) {
            SliderScreen()
        }

        composable(NavigationItem.Card.route) {
            ArtistCardScreen()
        }

        composable(NavigationItem.Box.route) {
            BoxLayoutScreen()
        }

        composable(NavigationItem.Responsive.route) {
            ResponsiveScreen()
        }

        composable(NavigationItem.TipCalculator.route) {
            TipCalculator()
        }
    }
}

@Composable
fun TopBar(onRouteChange: (NavigationItem) -> Unit, screenTitle: String = "Compose UI") {
   TopAppBar(
        title = { Text(screenTitle)},
        actions = {
            IconButton(onClick = { onRouteChange(NavigationItem.TipCalculator) }) {
                Icon(imageVector = Icons.Outlined.Restaurant, contentDescription = "Tip Calculator")
            }
            TopBarMenu(onRouteChange)
        }
   )
}

@Composable
fun TopBarMenu(onRouteChange: (NavigationItem) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val menuItems = listOf(
        NavigationItem.TextField,
        NavigationItem.Button,
        NavigationItem.RadioButton,
        NavigationItem.Switch,
        NavigationItem.CheckBox,
        NavigationItem.Dropdown,
        NavigationItem.Slider,
        NavigationItem.Divider,
        NavigationItem.Card,
        NavigationItem.Box,
        NavigationItem.Responsive,
        NavigationItem.Divider,
        NavigationItem.TipCalculator
    )

    Box(Modifier.wrapContentSize(Alignment.TopEnd)) {
        IconButton(onClick = {
            expanded = true
        }) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = "More..."
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {

            menuItems.forEach { menuItem ->
                if (menuItem.title == "Divider") {
                    Divider()
                } else {
                    DropdownMenuItem(onClick = {
                        expanded = false
                        onRouteChange(menuItem)
                    }) {
                        Text(menuItem.title)
                    }
                }
            }
        }
    }
}
