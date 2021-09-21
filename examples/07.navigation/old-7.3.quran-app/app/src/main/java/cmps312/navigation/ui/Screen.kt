package cmps312.navigation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import cmps312.navigation.R

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null, val iconResourceId:Int? = null) {
    object Quran: Screen(route = "quran", title = "Quran", iconResourceId = R.drawable.ic_quran)
    object Verses: Screen(route = "verses", title = "Surah Verses", iconResourceId = R.drawable.ic_quran)

    object Search : Screen(route = "search", title = "Search", icon = Icons.Outlined.Search)
    object Settings : Screen(route = "settings", title = "Settings", icon = Icons.Outlined.Settings)
}