package qa.edu.cmps312.compose

sealed class NavigationItem(var route: String, var title: String, var icon: Int? = null) {
    object Button : NavigationItem("button", "Button")
    object RadioButton : NavigationItem("radiobutton", "Radio Button")
    object Switch : NavigationItem("switch", "Switch")
    object Responsive : NavigationItem("responsive", "Responsive Screen (Responsive Design)")

    object Divider: NavigationItem("Divider", "Divider")
}