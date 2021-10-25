package cmps312.coroutines.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object WhyCoroutines : Screen(route = "why", title = "Why", icon = Icons.Outlined.DeviceUnknown)
    object CancelCoroutine : Screen(route = "cancel", title = "Cancel", icon = Icons.Outlined.PhonelinkErase)
    object StockQuote : Screen(route = "quote", title = "Stock Quote", icon = Icons.Outlined.PriceCheck)
    object StockQuotes : Screen(route = "quotes", title = "Stock Quotes", icon = Icons.Outlined.Splitscreen)
}