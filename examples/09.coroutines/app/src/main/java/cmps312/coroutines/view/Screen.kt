package cmps312.navigation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeviceUnknown
import androidx.compose.material.icons.outlined.PhonelinkErase
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null, val iconResourceId:Int? = null) {
    object WhyCoroutines : Screen(route = "why", title = "Why Coroutines", icon = Icons.Outlined.DeviceUnknown)
    object CancelCoroutine : Screen(route = "cancel", title = "Cancel Coroutine", icon = Icons.Outlined.PhonelinkErase)
}