package cmps312.yalapay.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import cmps312.yalapay.R
import cmps312.yalapay.view.Screen

@Composable
fun Drawer(navController: NavController) {
    val drawerItems = listOf(
        Screen.InvoiceReport,
        Screen.ChequeReport
    )
    Column(
        modifier = Modifier.background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.yalapay), contentDescription = "Report Image",
            Modifier.fillMaxWidth()
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        drawerItems.forEach { ditem ->
            var selected = if (currentRoute == ditem.route) true else false
            Item(ditem, selected = selected, { navController.navigate(ditem.route) })
        }
    }
}


@Composable
fun Item(screen: Screen, selected: Boolean, onDrawerItemSelected: () -> Unit) {
    var backColor = Color.White
    if (selected == true) {
        backColor = MaterialTheme.colors.primary
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDrawerItemSelected() }
            .background(backColor)
            .padding(10.dp)
    ) {
        Icon(imageVector = screen.icon, contentDescription = "Report Icon")
        Spacer(Modifier.width(10.dp))
        Text(text = screen.title)
    }


}