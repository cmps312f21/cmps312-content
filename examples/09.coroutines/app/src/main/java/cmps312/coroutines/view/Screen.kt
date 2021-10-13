package cmps312.coroutines.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object WhyCoroutines : Screen(route = "why", title = "Why Coroutines", icon = Icons.Outlined.DeviceUnknown)
    object CancelCoroutine : Screen(route = "cancel", title = "Cancel Coroutine", icon = Icons.Outlined.PhonelinkErase)
    object ParallelCoroutine : Screen(route = "parallel", title = "Parallel Coroutine", icon = Icons.Outlined.Splitscreen)
}