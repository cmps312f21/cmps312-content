package qa.edu.cmps312.compose

sealed class NavigationItem(var route: String, var title: String, var icon: Int? = null) {
    object Responsive : NavigationItem("responsive", "Responsive Screen (Responsive Design)")
    object Buttons : NavigationItem("buttons", "Buttons")
    object Divider: NavigationItem("Divider", "Divider")
}