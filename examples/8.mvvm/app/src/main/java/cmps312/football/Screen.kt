package cmps312.football

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.SportsSoccer
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Score: Screen(route = "score", title = "Score", icon = Icons.Outlined.SportsSoccer)
    object Users: Screen(route = "users", title = "Users", icon = Icons.Outlined.People)
}