package qu.cmps312.shoppinglist.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import qu.cmps312.shoppinglist.view.screens.*

/**
 * It receives navController to navigate between screens
 */
@ExperimentalFoundationApi
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AppNavigator(
    navController: NavHostController,
    padding: PaddingValues,
    startDestination: String
) {
    NavHost(
        navController = navController,
        //set the start destination as home
        startDestination = startDestination,
        //Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding)
    ) {

        /* Define the app Navigation Graph
           = possible routes a user can take through the app
        */
        composable(Screen.ShoppingList.route) {
            /* Load the ShoppingListScreen and when add/edit item is clicked
            then navigate to the ShoppingItem screen */
            ShoppingListScreen(
                onAddItem = {
                    navController.navigate(Screen.ShoppingItem.route)
                },
                onEditItem = {
                    navController.navigate(Screen.ShoppingItem.route)
                }
            )
        }

        composable(Screen.ShoppingItem.route) {
            /* Load the ShoppingListScreen and when edit item is clicked
            then navigate to the ShoppingItem screen and pass the select itemId as a parameter */
            ShoppingItemScreen(onNavigateBack = { navController.navigateUp() })
        }

        composable(Screen.CloudStorage.route) {
            StorageScreen()
        }

        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Screen.ShoppingList.route)
            },
            onSignup = {
                navController.navigate(Screen.Signup.route)
            })
        }

        composable(Screen.Signup.route) {
            SignupScreen(onNavigateBack = {
                navController.navigate(Screen.ShoppingList.route)
            })
        }
    }
}