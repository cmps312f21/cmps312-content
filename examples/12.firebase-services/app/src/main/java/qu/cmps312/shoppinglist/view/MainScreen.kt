package qu.cmps312.shoppinglist.view

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    AppNavigator(navController)
}
