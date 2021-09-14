package cmps312.compose

sealed class NavigationItem(var route: String, var title: String, var icon: Int? = null) {
    object TextField: NavigationItem("textfield", "TextField")
    object Button : NavigationItem("button", "Buttons")
    object RadioButton : NavigationItem("radiobutton", "Radio Buttons")
    object Switch : NavigationItem("switch", "Switch")
    object Responsive : NavigationItem("responsive", "Responsive Design")
    object TipCalculator : NavigationItem("TipCalculator", "Tip Calculator", R.drawable.ic_quran)
    object Divider: NavigationItem("Divider", "Divider")
}